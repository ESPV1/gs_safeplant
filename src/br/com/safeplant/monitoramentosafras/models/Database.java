package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.IOperacoesPadrao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Gerencia operações de persistência em arquivos JSON para entidades do sistema.
 *
 * @param <T> tipo da entidade gerenciada pelo banco de dados
 */
public class Database<T extends IOperacoesPadrao> implements IDatabase<T> {
    /**
     * Instância do Gson utilizada para serialização e desserialização de objetos Java para JSON.
     */
    private final Gson gson;

    /**
     * Inicializa o banco de dados configurando o Gson com formatação legível.
     */
    public Database() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * {@inheritDoc}
     * Os registros são lidos de um arquivo JSON correspondente à entidade.
     * Em caso de erro, retorna uma lista vazia.
     */
    public ArrayList<T> lerRegistro(Class<T> classe) {
        try {
            Path path = getPathBanco(classe);
            String json = Files.readString(path, StandardCharsets.UTF_8);
            Type type = TypeToken.getParameterized(ArrayList.class, classe).getType();

            ArrayList<T> registros = gson.fromJson(json, type);
            return registros != null ? registros : new ArrayList<T>();
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro na leitura dos dados");
            ex.printStackTrace();
            return new ArrayList<T>();
        }
    }

    /**
     * {@inheritDoc}
     * O registro é persistido num arquivo JSON correspondente à entidade.
     * Em caso de erro, retorna {@code false}.
     */
    public boolean criarRegistro(T entidade, Class<T> classe) {
        try {
            Path path = getPathBanco(entidade.getClass());
            if (path == null)
                return false;

            ArrayList<T> registros = lerRegistro(classe);
            if (registros == null)
                registros = new ArrayList<>();

            registros.add(entidade);

            Files.writeString(path, gson.toJson(registros), StandardCharsets.UTF_8);

            return true;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro ao salvar o registro");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * A entidade é localizada pelo seu ‘ID’ e substituída no arquivo JSON correspondente.
     * Retorna {@code false} se a entidade não for encontrada ou ocorrer erro.
     */
    public boolean editarRegistro(T entidade, Class<T> classe) {
        try {
            Path path = getPathBanco(entidade.getClass());
            if (path == null)
                return false;

            ArrayList<T> registros = lerRegistro(classe);
            if (registros == null)
                return false;

            int indexEndereco = -1;
            for (int i = 0; i < registros.size(); i++) {
                T e = registros.get(i);
                if (e.getId().equals(entidade.getId())) {
                    indexEndereco = i;
                    break;
                }
            }

            if (indexEndereco < 0)
                return false;

            registros.set(indexEndereco, entidade);

            Files.writeString(path, gson.toJson(registros), StandardCharsets.UTF_8);

            return true;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro ao salvar o registro");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * A entidade é localizada pelo seu ID e removida do arquivo JSON correspondente.
     * Retorna {@code false} se a entidade não for encontrada ou ocorrer erro.
     */
    public boolean removerRegistro(T entidade, Class<T> classe) {
        try {
            Path path = getPathBanco(entidade.getClass());
            if (path == null)
                return false;

            ArrayList<T> registros = lerRegistro(classe);
            if (registros == null)
                return false;

            int indexEndereco = -1;
            for (int i = 0; i < registros.size(); i++) {
                T e = registros.get(i);
                if (e.getId().equals(entidade.getId())) {
                    indexEndereco = i;
                    break;
                }
            }
            if (indexEndereco < 0)
                return false;

            registros.remove(indexEndereco);

            Files.writeString(path, gson.toJson(registros), StandardCharsets.UTF_8);

            return true;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro ao remover o registro");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * Em caso de erro na desserialização, retorna {@code null}.
     */
    public T converterJsonParaJava(String json, Class<T> classe) {
        try {
            return gson.fromJson(json, classe);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro ao remover o registro");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Retorna o nome do arquivo JSON correspondente ao nome da entidade informada.
     *
     * @param nomeEntidade nome simples da entidade
     * @return nome do arquivo JSON, ou string vazia se não encontrado
     */
    private String getNomeBanco(String nomeEntidade) {
        switch (nomeEntidade.toLowerCase()) {
            case "usuario":
                return "db_user.json";
            case "agricultor":
                return "db_agricultor.json";
            case "safra":
                return "db_safra.json";
            case "produto":
                return "db_prod.json";
            case "endereco":
                return "db_endereco.json";
            default:
                return "";
        }
    }

    /**
     * Formata o caminho absoluto do arquivo JSON correspondente à classe informada.
     *
     * @param classe tipo da entidade cujo arquivo será localizado
     * @return {@link Path} do arquivo JSON, ou {@code null} se a entidade não for reconhecida
     */
    private Path getPathBanco(Class<?> classe) {
        String nomeBanco = getNomeBanco(classe.getSimpleName());
        if (nomeBanco.isEmpty())
            return null;

        return Paths.get(System.getProperty("user.dir"), "resources", nomeBanco);
    }
}

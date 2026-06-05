package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Database<T> implements IDatabase<T> {
    private final Gson gson;

    public Database() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * @return
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
     * @param entidade
     * @return
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
     * @param entidade
     * @return
     */
    public boolean editarRegistro(T entidade, Class<T> classe) {
        return false;
    }

    /**
     * @param entidade
     * @return
     */
    public boolean removerRegistro(T entidade, Class<T> classe) {
        try {
            Path path = getPathBanco(entidade.getClass());
            if (path == null)
                return false;

            ArrayList<T> registros = lerRegistro(classe);
            if (registros == null)
                registros = new ArrayList<>();

            registros.remove(entidade);

            Files.writeString(path, gson.toJson(registros), StandardCharsets.UTF_8);

            return true;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro ao remover o registro");
            ex.printStackTrace();
            return false;
        }
    }

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
            default:
                return "";
        }
    }

    private Path getPathBanco(Class<?> classe) {
        String nomeBanco = getNomeBanco(classe.getSimpleName());
        if (nomeBanco.isEmpty())
            return null;

        return Paths.get(System.getProperty("user.dir"), "resources", nomeBanco);
    }
}

package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database<T> implements IDatabase<T> {
    private final Gson gson;

    public Database() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) ->
                        LocalDateTime.parse(json.getAsString()))
                .create();
    }

    /**
     * @return
     */
    public ArrayList<T> lerRegistro(Class<T> classe) {
        try {
            String nomeBanco = getNomeBanco(classe.getSimpleName());
            if (nomeBanco.isEmpty())
                return null;

            URL url = getClass().getClassLoader().getResource(nomeBanco);
            String jsonUsers = Files.readString(Paths.get(url.toURI()), StandardCharsets.UTF_8);
            Type type = TypeToken.getParameterized(ArrayList.class, classe).getType();

            return gson.fromJson(jsonUsers, type);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro na leitura dos dados");
            return null;
        }
    }

    /**
     * @param entidade
     * @return
     */
    public boolean criarRegistro(T entidade) {
        return false;
    }

    /**
     * @param entidade
     * @return
     */
    public boolean editarRegistro(T entidade) {
        return false;
    }

    /**
     * @param entidade
     * @return
     */
    public boolean removerRegistro(T entidade) {
        return false;
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
}

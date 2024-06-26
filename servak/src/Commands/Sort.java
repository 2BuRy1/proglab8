package Commands;

import Managers.CollectionManager;
import Network.Request;
import Network.Response;

import java.io.Serial;
import java.io.Serializable;

public class Sort extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    private CollectionManager collectionManager;
    public Sort(CollectionManager collectionManager) {
        super("sort");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request, CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        collectionManager.sort();
        return new Response("Коллекция успешно отсортирована");
    }
}

package edu.poly.tousantigaspi.repositories;

import android.content.Context;

import com.google.gson.JsonArray;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.object.ManuallyProduct;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.DateCalculator;
import edu.poly.tousantigaspi.util.factory.AbstractFactory;
import edu.poly.tousantigaspi.util.factory.FactoryProvider;
import edu.poly.tousantigaspi.util.factory.ProductFactory;
import edu.poly.tousantigaspi.util.request.AddProductRequest;
import edu.poly.tousantigaspi.util.request.CreateFrigoRequest;
import edu.poly.tousantigaspi.util.request.DeleteFrigoRequest;
import edu.poly.tousantigaspi.util.request.EditFrigoRequest;
import edu.poly.tousantigaspi.util.request.GetProductsRequest;
import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrigoRepository {

    private static FrigoRepository instance;

    public static FrigoRepository getInstance() {
        if (instance == null) {
            instance = new FrigoRepository();
        }
        return instance;
    }

    public void getFrigos(Context context,String username, FrigoModel model) {
        setFrigos(context,username, model);
    }

    private void setFrigos(Context context,String username, FrigoModel model) {
        GetRequestWithUsername getRequestWithUsername = new GetRequestWithUsername();
        getRequestWithUsername.setUsername(username);

        Call<JsonArray> getFrigoResponseCall = ApiClient.getFrigoService().getFrigo(getRequestWithUsername);


        getFrigoResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray frigo_array = response.body();

                    ArrayList<Frigo> frigos = new ArrayList<>();
                    frigo_array.forEach(x -> frigos.add(new Frigo(x.getAsJsonObject().get("_id").toString().replace("\"", "")
                            , x.getAsJsonObject().get("name").toString().replace("\"", "")
                            , new ArrayList<Product>())));

                    getProducts(context,frigos, model);
                } else {
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void editFrigo(String newName, String id, FrigoModel model) {
        EditFrigoRequest getRequestWithUsername = new EditFrigoRequest();
        getRequestWithUsername.setId(id);
        getRequestWithUsername.setName(newName);

        Call<String> getFrigoResponseCall = ApiClient.getFrigoService().editFrigo(getRequestWithUsername);


        getFrigoResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    model.notifyObs(model);
                } else {
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void getProducts(Context context,ArrayList<Frigo> frigos, FrigoModel model) {
        for (Frigo frigo : frigos) {
            GetProductsRequest getProductsRequest = new GetProductsRequest();
            getProductsRequest.setId(frigo.getId());

            Call<JsonArray> getProductResponseCall = ApiClient.getProductService().getProduct(getProductsRequest);

            getProductResponseCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.isSuccessful()) {
                        AbstractFactory<Product> factory = FactoryProvider.getFactory(FactoryProvider.PRODUCT);
                        JsonArray product_array = response.body();

                        ArrayList<Product> products = new ArrayList<>();
                        product_array.forEach(x -> {
                            Product newProduct = factory.build(ProductFactory.MANUALLY);
                            newProduct.setId(x.getAsJsonObject().get("_id").toString().replace("\"", ""));
                            newProduct.setQuantity(Integer.parseInt(x.getAsJsonObject().get("quantity").toString().replace("\"", "")));
                            newProduct.setDateRemaining(new DateCalculator().calculateDaysRemaining(context,x.getAsJsonObject().get("date").toString().replace("\"", "").split("T")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            newProduct.setName(x.getAsJsonObject().get("name").toString().replace("\"", ""));
                            products.add(newProduct);
                        });


                        frigo.setProducts(products);
                        model.setFrigos(frigos);
                        model.loadPastDLCProduct();
                        model.notifyObs(model);
                    } else {
                        System.out.println(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }
    }

    public void addProduct(Product product, String id, FrigoModel model,String date) {
        System.out.println(date);
        AddProductRequest getProductsRequest = new AddProductRequest();
        getProductsRequest.setId(id);
        getProductsRequest.setName(product.getName());
        getProductsRequest.setDate(date);
        getProductsRequest.setQuantity(product.getQuantity());

        Call<String> getProductResponseCall = ApiClient.getProductService().addProduct(getProductsRequest);

        getProductResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("Product created");
                } else {
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void deleteFrigo(String id) {
        DeleteFrigoRequest deleteFrigoRequest = new DeleteFrigoRequest();
        deleteFrigoRequest.setId(id);


        Call<String> deleteFrigoResponseCall = ApiClient.getFrigoService().deleteFrigo(deleteFrigoRequest);

        deleteFrigoResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("Frigo deleted");
                } else {
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void addFrigo(Context context, String frigoName, String username,FrigoModel model) {
        CreateFrigoRequest createFrigoRequest = new CreateFrigoRequest();
        createFrigoRequest.setName(frigoName);
        createFrigoRequest.setUsername(username);


        Call<String> createFrigoResponseCall = ApiClient.getFrigoService().createFrigo(createFrigoRequest);

        createFrigoResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("Frigo is created");
                    model.loadFrigo(context,username);
                } else {
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}


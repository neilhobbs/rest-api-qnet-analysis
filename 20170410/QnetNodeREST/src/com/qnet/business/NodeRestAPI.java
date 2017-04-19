package com.qnet.business;

import com.qnet.statistic.MoExcuter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/node")

public class NodeRestAPI {
    @Path("{param}")
    @GET
    @Produces("application/json")
    public Response MoCounter(@PathParam("param") String[] param)  throws JSONException, InterruptedException, ExecutionException  {
        JSONObject jsonObject = new JSONObject();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<?>> listFut = new ArrayList<Future<?>>();
        for (int i = 0; i < param.length; i++) {
            String inPutFile = param[i];
            Runnable worker = new MoExcuter(inPutFile);
            Future<?> future = (Future<?>) executor.submit(worker);
            listFut.add(future);
            Thread.sleep(1000);
        }
        for (Future<?> fut : listFut) {
           fut.get();
        }
        executor.shutdown();
        
        //FIXME, return result after counter
        jsonObject.put("result", "");
        String result = "@Produces(\"application/json\")" + jsonObject;
        return Response.status(200).entity(result).build();
    }
}

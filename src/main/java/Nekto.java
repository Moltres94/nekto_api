import core.*;

public class Nekto {

    public static void main(String[] args) {
        NektoAPI api = new NektoAPI();
        //Contain token and id. Could be use for save and reuse
        NektoUser user = new NektoUser();
        //Just device static info
        NektoDeivce deivce = new NektoDeviceImpl();
        api.connect(user);
        //set token if you have. or request new token
        if(false)
            api.send(new NektoRequest.SendToken(deivce, "TOKEN HERE"));
        else
            api.send(new NektoRequest.GetToken(deivce));
        //get server stats
        api.send(new NektoRequest.OnlineTrack());
        //I dont know what is it
        //api.send("2");
        //start search with current filters
        /*api.send(new NektoRequest.SearchRun(new NektoSearchFilter(
                false,
                false,
                true,
                22,
                25,
                "M",
                22,
                25,
                "F"
        )));*/
        //api.close();
        //Call close to exit from application
        //System.close(0);
    }

}

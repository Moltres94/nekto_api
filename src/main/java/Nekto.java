import core.*;

public class Nekto {

    public static void main(String[] args) {
        NektoGui ng = new NektoGui();
        NektoAPI api = new NektoAPI(ng);
        ng.setApi(api);
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
        api.send(new NektoRequest.OnlineTrack(true));

        //api.close();
        //Call close to exit from application
        //System.close(0);
    }

}

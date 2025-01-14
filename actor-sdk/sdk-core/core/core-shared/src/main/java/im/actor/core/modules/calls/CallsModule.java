package im.actor.core.modules.calls;

import java.util.ArrayList;
import java.util.HashMap;

import im.actor.core.api.ApiOutPeer;
import im.actor.core.api.ApiPeerType;
import im.actor.core.api.rpc.RequestDoCall;
import im.actor.core.entity.CallState;
import im.actor.core.entity.Peer;
import im.actor.core.entity.User;
import im.actor.core.network.RpcCallback;
import im.actor.core.network.RpcException;
import im.actor.core.viewmodel.CallModel;
import im.actor.core.viewmodel.CommandCallback;
import im.actor.core.webrtc.WebRTCProvider;
import im.actor.core.api.rpc.ResponseDoCall;
import im.actor.core.modules.AbsModule;
import im.actor.core.modules.ModuleContext;
import im.actor.core.viewmodel.Command;
import im.actor.runtime.actors.ActorRef;

import static im.actor.runtime.actors.ActorSystem.system;

public class CallsModule extends AbsModule {

    public static final int CALL_TIMEOUT = 10;

    public static final String TAG = "CALLS";

    private WebRTCProvider provider;
    private ActorRef callManager;
    private HashMap<Long, CallModel> callModels = new HashMap<>();

    public CallsModule(ModuleContext context) {
        super(context);

        provider = context().getConfiguration().getWebRTCProvider();
    }

    public void run() {
        if (provider == null) {
            return;
        }

        callManager = system().actorOf("calls/manager", CallManagerActor.CONSTRUCTOR(context()));
    }

    public void spawnNewModel(long id, Peer peer, ArrayList<Integer> activeMembers, CallState state) {
        callModels.put(id, new CallModel(id, peer, activeMembers, state));
    }

    public CallModel getCall(long id) {
        return callModels.get(id);
    }

    public ActorRef getCallManager() {
        return callManager;
    }

    public Command<ResponseDoCall> makeCall(final int uid) {
        return new Command<ResponseDoCall>() {
            @Override
            public void start(final CommandCallback<ResponseDoCall> callback) {
//                User u = users().getValue(uid);
//                request(new RequestDoCall(new ApiOutPeer(ApiPeerType.PRIVATE, u.getUid(), u.getAccessHash()), CALL_TIMEOUT), new RpcCallback<ResponseDoCall>() {
//                    @Override
//                    public void onResult(final ResponseDoCall response) {
//                        callManager.send(new CallManagerActor.OnOutgoingCall(response.getCallId(), uid));
//                        callback.onResult(response);
//                    }
//
//                    @Override
//                    public void onError(RpcException e) {
//                        callback.onError(e);
//                    }
//                });
            }
        };
    }

    public void endCall(long callId) {
        callManager.send(new CallManagerActor.EndCall(callId));
    }

    public void answerCall(long callId) {
        callManager.send(new CallManagerActor.AnswerCall(callId));
    }
}

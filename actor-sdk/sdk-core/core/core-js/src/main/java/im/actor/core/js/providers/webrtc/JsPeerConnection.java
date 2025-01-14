package im.actor.core.js.providers.webrtc;

import com.google.gwt.core.client.JavaScriptObject;

import im.actor.core.js.entity.JsClosure;
import im.actor.core.js.entity.JsClosureError;
import im.actor.runtime.promise.Promise;
import im.actor.runtime.promise.PromiseFunc;
import im.actor.runtime.promise.PromiseResolver;

public class JsPeerConnection extends JavaScriptObject {

    public static native JsPeerConnection create(JsPeerConnectionConfig config)/*-{
        return {peerConnection: new webkitRTCPeerConnection(config)};
    }-*/;

    protected JsPeerConnection() {

    }

    public final native void setListener(JsPeerConnectionListener listener)/*-{
        this.peerConnection.onicecandidate = function(candidate) {
            if (candidate.candidate == null) {
                listener.@im.actor.core.js.providers.webrtc.JsPeerConnectionListener::onIceCandidatesEnded(*)();
            } else {
                listener.@im.actor.core.js.providers.webrtc.JsPeerConnectionListener::onIceCandidate(*)(candidate.candidate);
            }
        };
        this.peerConnection.onaddstream = function(event) {
            listener.@im.actor.core.js.providers.webrtc.JsPeerConnectionListener::onStreamAdded(*)(event.stream);
        }
    }-*/;

    public final Promise<JsSessionDescription> setLocalDescription(final JsSessionDescription description) {
        return new Promise<>(new PromiseFunc<JsSessionDescription>() {
            @Override
            public void exec(final PromiseResolver<JsSessionDescription> resolver) {
                setLocalDescription(description, new JsClosure() {
                    @Override
                    public void callback() {
                        resolver.result(description);
                    }
                }, new JsClosureError() {
                    @Override
                    public void onError(JavaScriptObject error) {
                        resolver.error(new RuntimeException());
                    }
                });
            }
        });
    }

    public final Promise<JsSessionDescription> setRemoteDescription(final JsSessionDescription description) {
        return new Promise<>(new PromiseFunc<JsSessionDescription>() {
            @Override
            public void exec(final PromiseResolver<JsSessionDescription> resolver) {
                setRemoteDescription(description, new JsClosure() {
                    @Override
                    public void callback() {
                        resolver.result(description);
                    }
                }, new JsClosureError() {
                    @Override
                    public void onError(JavaScriptObject error) {
                        resolver.error(new RuntimeException());
                    }
                });
            }
        });
    }

    public final native void addStream(JsMediaStream stream)/*-{
        this.peerConnection.addStream(stream);
    }-*/;

    public final native void addIceCandidate(int label, String candidate)/*-{
        this.peerConnection.addIceCandidate(new RTCIceCandidate({sdpMLineIndex: label, candidate: candidate}));
    }-*/;

    public final native void close()/*-{
        this.peerConnection.close();
    }-*/;

    public final Promise<JsSessionDescription> createOffer() {
        return new Promise<>(new PromiseFunc<JsSessionDescription>() {
            @Override
            public void exec(final PromiseResolver<JsSessionDescription> resolver) {
                createOffer(new JsSessionDescriptionCallback() {
                    @Override
                    public void onOfferCreated(JsSessionDescription offer) {
                        resolver.result(offer);
                    }

                    @Override
                    public void onOfferFailure() {
                        resolver.error(new RuntimeException("Offer failure"));
                    }
                });
            }
        });
    }

    public final Promise<JsSessionDescription> createAnswer() {
        return new Promise<>(new PromiseFunc<JsSessionDescription>() {
            @Override
            public void exec(final PromiseResolver<JsSessionDescription> resolver) {
                createAnswer(new JsSessionDescriptionCallback() {
                    @Override
                    public void onOfferCreated(JsSessionDescription offer) {
                        resolver.result(offer);
                    }

                    @Override
                    public void onOfferFailure() {
                        resolver.error(new RuntimeException("Offer failure"));
                    }
                });
            }
        });
    }

    private final native void createOffer(JsSessionDescriptionCallback callback)/*-{

        var sdpConstraints = {
            'mandatory': {
                'OfferToReceiveAudio': true,
                'OfferToReceiveVideo': false
            }
        };


        this.peerConnection.createOffer(function(offer) {
            callback.@im.actor.core.js.providers.webrtc.JsSessionDescriptionCallback::onOfferCreated(*)(offer);
        }, function(error) {
        $wnd.console.warn(error);
            callback.@im.actor.core.js.providers.webrtc.JsSessionDescriptionCallback::onOfferFailure(*)();
        }, sdpConstraints);
    }-*/;

    private final native void createAnswer(JsSessionDescriptionCallback callback)/*-{

        var sdpConstraints = {
            'mandatory': {
                'OfferToReceiveAudio': true,
                'OfferToReceiveVideo': false
            }
        };

        this.peerConnection.createAnswer(function(offer) {
            callback.@im.actor.core.js.providers.webrtc.JsSessionDescriptionCallback::onOfferCreated(*)(offer);
        }, function(error) {
            $wnd.console.warn(error);
            callback.@im.actor.core.js.providers.webrtc.JsSessionDescriptionCallback::onOfferFailure(*)();
        }, sdpConstraints);
    }-*/;


    private final native void setRemoteDescription(JsSessionDescription description, JsClosure closure, JsClosureError error)/*-{
        this.peerConnection.setRemoteDescription(description, function() {
            closure.@im.actor.core.js.entity.JsClosure::callback(*)();
        }, function(e) {
            $wnd.console.warn(e);
            error.@im.actor.core.js.entity.JsClosureError::onError(*)(e);
        });
    }-*/;

    public final native void setLocalDescription(JsSessionDescription description, JsClosure closure, JsClosureError error)/*-{
        this.peerConnection.setLocalDescription(description, function() {
            closure.@im.actor.core.js.entity.JsClosure::callback(*)();
        }, function(e) {
            $wnd.console.warn(e);
            error.@im.actor.core.js.entity.JsClosureError::onError(*)(e);
        });
    }-*/;
}

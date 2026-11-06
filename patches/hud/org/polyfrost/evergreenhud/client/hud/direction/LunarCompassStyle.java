package org.polyfrost.evergreenhud.client.hud.direction;

import kotlin.Unit;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ScopeUpdateScope;
import java.lang.reflect.Method;
import org.polyfrost.compose.render.PolyColor;
import org.polyfrost.compose.render.RenderContext;

/** Classic compass ribbon. Uses the existing Direction HUD's placement/editor. */
public final class LunarCompassStyle {
    private static final String[] DIRECTIONS={"N","NE","E","SE","S","SW","W","NW"};
    private static boolean announced;
    private static final Class<?> VIEW=viewClass();
    private static final Method BEARING=method(VIEW,"getBearing"),SPAN=method(VIEW,"getSpan"),
        INTERCARDINALS=method(VIEW,"getShowIntercardinals"),FADE=method(VIEW,"getFadeEdges"),HEADING=method(VIEW,"getShowHeading");
    private static final Method LABEL=method(DirectionHud.class,"Label",String.class,PolyColor.class,float.class,float.class,float.class,float.class,Composer.class,int.class);
    private static final Method TICKS=method(DirectionHud.class,"Ticks",VIEW,float.class,float.class,float.class,float.class,Composer.class,int.class);
    private LunarCompassStyle(){}
    private static Class<?> viewClass(){try{return Class.forName(DirectionHud.class.getName()+"$View");}catch(ClassNotFoundException e){throw new ExceptionInInitializerError(e);}}
    private static Method method(Class<?> owner,String name,Class<?>... types){try{Method m=owner.getDeclaredMethod(name,types);m.setAccessible(true);return m;}catch(ReflectiveOperationException e){throw new ExceptionInInitializerError(e);}}
    public static float delta(float angle,float bearing){return ((angle-bearing+540f)%360f)-180f;}
    public static String label(int heading,boolean intercardinals){
        if(heading%90==0 || intercardinals&&heading%45==0)return DIRECTIONS[heading/45];
        return Integer.toString(heading);
    }
    private static int fade(int argb,float delta,float halfSpan,boolean enabled){
        if(!enabled)return argb;
        float edge=Math.abs(delta)/halfSpan;
        float opacity=edge<=0.65f?1f:Math.max(0f,(1f-edge)/0.35f);
        return (argb&0x00ffffff)|(Math.round((argb>>>24)*opacity)<<24);
    }
    private static PolyColor faded(PolyColor color,float delta,float half,boolean enabled){return new PolyColor(fade(color.getRawArgb(),delta,half,enabled),color.getChroma(),color.getChromaSpeed());}
    public static void render(DirectionHud hud,Object view,Composer incoming,int changed){
        Composer composer=incoming.startRestartGroup(1849635912);
        try{
            float bearing=((Number)BEARING.invoke(view)).floatValue(),span=((Number)SPAN.invoke(view)).floatValue();
            boolean intercardinals=(Boolean)INTERCARDINALS.invoke(view),fade=(Boolean)FADE.invoke(view),heading=(Boolean)HEADING.invoke(view);
            float width=hud.getScaledWidth(),height=hud.getScaledHeight(),header=heading?10f*hud.getTextScale():0f;
            float ribbon=Math.max(12f,height-header),tickStrip=Math.max(1f,Math.min(8f,ribbon-11f));
            PolyColor fg=new PolyColor(hud.getTextColor(),hud.getTextChroma(),hud.getTextChromaSpeed());
            if(heading)LABEL.invoke(hud,Integer.toString(Math.round(bearing))+"\u00b0",fg,width,header,0f,0f,composer,0);
            TICKS.invoke(hud,view,width,ribbon,header,tickStrip,composer,0);
            float half=span*.5f,labelWidth=24f*hud.getTextScale();
            for(int angle=0;angle<360;angle+=15){
                composer.startReplaceGroup(1849640000+angle);
                float offset=delta(angle,bearing);
                if(Math.abs(offset)<=half)LABEL.invoke(hud,label(angle,intercardinals),faded(fg,offset,half,fade),labelWidth,Math.max(8f,ribbon-tickStrip-2f),width*.5f+offset*width/span-labelWidth*.5f,header+tickStrip+2f,composer,0);
                composer.endReplaceGroup();
            }
        }catch(ReflectiveOperationException e){throw new IllegalStateException("Compass HUD label integration failed",e.getCause()==null?e:e.getCause());}
        ScopeUpdateScope scope=composer.endRestartGroup();
        if(scope!=null)scope.updateScope((next,flags)->{render(hud,view,next,changed|1);return Unit.INSTANCE;});
    }
    public static Unit draw(float bearing,float span,boolean ticks,boolean intercardinals,boolean fadeEdges,
                            DirectionHud hud,RenderContext ctx,float x,float y,float width,float height){
        if(!(span>0f)||!Float.isFinite(bearing)||width<=0f||height<=0f)return Unit.INSTANCE;
        if(!announced){announced=true;System.out.println("[EvergreenHUD] Classic compass ribbon active (degrees, directions and ticks)");}
        float half=span*.5f,tickStrip=Math.max(1f,Math.min(8f,height-11f));
        float center=x+width*.5f;
        for(int heading=0;heading<360;heading+=15){
            float offset=delta(heading,bearing);
            if(Math.abs(offset)>half)continue;
            float px=center+offset*width/span;
            if(ticks){
                float tickHeight=heading%45==0?tickStrip:tickStrip*.5f;
                ctx.line(Math.round(px),y,Math.round(px),y+tickHeight,faded(hud.getTickColor(),offset,half,fadeEdges),1f);
            }
        }
        ctx.line(Math.round(center),y,Math.round(center),y+tickStrip+2f,hud.getMarkerColor(),1f);
        return Unit.INSTANCE;
    }
}

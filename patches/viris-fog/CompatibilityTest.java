import com.viris.compat.everness.FogConfigMigration;
import com.viris.compat.everness.NetherFogPolicy;
import java.nio.file.*;
import java.util.Arrays;

public final class CompatibilityTest {
    private static int checks;
    private static void check(boolean value){if(!value)throw new AssertionError("Check "+checks);checks++;}
    private static Path fixture(Path root,String name,String config)throws Exception{Path p=root.resolve(name);Files.createDirectories(p.resolve("config"));Files.writeString(p.resolve("config/gammautils.json"),config);return p;}
    public static void main(String[] args)throws Exception{
        Path root=Files.createTempDirectory(Path.of(args[0]),"fog-");
        String original="{\r\n  \"gamma\": {\"value\": 1.0},\r\n  \"nightVision\": {\"enabled\": true, \"value\": 100.0, \"brightenFogColor\": true},\r\n  \"other\": {\"text\": \"äöü\"}\r\n}\r\n";
        Path p=fixture(root,"old",original);
        check(FogConfigMigration.migrate(p).equals("MIGRATED_WITH_BACKUP"));
        check(Files.readString(p.resolve("config/gammautils.json")).equals(original.replace("\"brightenFogColor\": true","\"brightenFogColor\": false")));
        try(var files=Files.list(p.resolve(".everness/compat-backups"))){var list=files.toList();check(list.size()==1);check(Files.readString(list.getFirst()).equals(original));}
        Files.writeString(p.resolve("config/gammautils.json"),original);
        check(FogConfigMigration.migrate(p).equals("ALREADY_CHECKED"));check(Files.readString(p.resolve("config/gammautils.json")).equals(original));
        p=fixture(root,"off","{\"nightVision\":{\"enabled\":true,\"brightenFogColor\":false}}");check(FogConfigMigration.migrate(p).equals("ALREADY_OFF"));check(!Files.exists(p.resolve(".everness/compat-backups")));
        p=fixture(root,"broken","{bad json");check(FogConfigMigration.migrate(p).equals("INVALID_JSON_SKIP"));check(!Files.exists(p.resolve(".everness/compat-migrations")));
        p=fixture(root,"wrong-type","{\"nightVision\":{\"brightenFogColor\":\"true\"}}");check(FogConfigMigration.migrate(p).equals("NO_SUPPORTED_KEY"));
        p=fixture(root,"absent","{\"nightVision\":{\"enabled\":true}}");check(FogConfigMigration.migrate(p).equals("NO_SUPPORTED_KEY"));
        p=fixture(root,"ambiguous","{\"nightVision\":{\"brightenFogColor\":true},\"other\":{\"brightenFogColor\":true}}");check(FogConfigMigration.migrate(p).equals("AMBIGUOUS_SKIP"));
        check(FogConfigMigration.migrate(root.resolve("missing")).equals("NOT_APPLICABLE"));
        p=fixture(root,"large"," ".repeat(524289));check(FogConfigMigration.migrate(p).equals("OVERSIZE_SKIP"));
        var a=NetherFogPolicy.extend(10,96,8,12.5f);check(a.end()==512);check(Math.abs(a.start()-53.333333f)<.001f);
        a=NetherFogPolicy.extend(10,96,17,12.5f);check(a.end()==1088);check(Math.abs(a.start()/a.end()-10f/96f)<.0001f);
        check(NetherFogPolicy.extend(10,96,64,100).end()==2048);
        check(NetherFogPolicy.extend(10,96,17,.25f).end()==128);
        check(NetherFogPolicy.extend(10,96,17,.1f).end()==96);
        check(NetherFogPolicy.extend(10,4096,17,100).end()==4096);
        check(NetherFogPolicy.extend(0,Float.POSITIVE_INFINITY,17,12.5f).end()==Float.POSITIVE_INFINITY);
        check(Float.isNaN(NetherFogPolicy.extend(0,Float.NaN,17,12.5f).end()));
        check(NetherFogPolicy.extend(10,96,0,12.5f).end()==96);
        check(NetherFogPolicy.extend(10,96,17,Float.NaN).end()==96);
        check(NetherFogPolicy.extend(100,96,17,12.5f).end()==96);
        System.out.println("FOG_COMPAT_TEST_PASS checks="+checks);
    }
}

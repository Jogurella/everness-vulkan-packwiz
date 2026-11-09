import com.viris.compat.everness.ChestConfigMigration;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class MigrationTest {
 static int checks;
 static void check(boolean value){if(!value)throw new AssertionError("Check "+checks);checks++;}
 static Path fixture(Path root,String name,String options,String config)throws Exception{
  Path p=root.resolve(name);Files.createDirectories(p.resolve("config"));Files.writeString(p.resolve("options.txt"),options);Files.writeString(p.resolve("config/enhanced_bes.properties"),config);return p;
 }
 public static void main(String[]args)throws Exception{
  Path run=Files.createTempDirectory(Path.of(args[0]),"migration-");
  String on="resourcePacks:[\"vanilla\",\"file/FA+Objects-v2.1.2.zip\"]\n";
  String config="# personal comment\r\nrender_enhanced_chests=true\r\nrender_enhanced_signs=true\r\nunrelated=value\r\n";
  Path p=fixture(run,"true",on,config);
  check(ChestConfigMigration.migrate(p).equals("MIGRATED_WITH_BACKUP"));
  check(Files.readString(p.resolve("config/enhanced_bes.properties")).equals(config.replace("render_enhanced_chests=true","render_enhanced_chests=false")));
  try(var backups=Files.list(p.resolve(".everness/compat-backups"))){var list=backups.toList();check(list.size()==1);check(Files.readString(list.getFirst()).equals(config));}
  Files.writeString(p.resolve("config/enhanced_bes.properties"),config);
  check(ChestConfigMigration.migrate(p).equals("ALREADY_CHECKED"));check(Files.readString(p.resolve("config/enhanced_bes.properties")).equals(config));
  p=fixture(run,"off",on,"render_enhanced_chests=false\nother=17\n");check(ChestConfigMigration.migrate(p).equals("ALREADY_OFF"));check(!Files.exists(p.resolve(".everness/compat-backups")));
  p=fixture(run,"no-pack","resourcePacks:[\"vanilla\"]\n",config);check(ChestConfigMigration.migrate(p).equals("NOT_APPLICABLE"));check(Files.readString(p.resolve("config/enhanced_bes.properties")).equals(config));
  p=fixture(run,"duplicate",on,"render_enhanced_chests=true\nrender_enhanced_chests=false\n");check(ChestConfigMigration.migrate(p).equals("AMBIGUOUS_SKIP"));check(!Files.exists(p.resolve(".everness/compat-backups")));
  p=fixture(run,"wrong-key",on,"render_enhanced_chests_custom=true\n");check(ChestConfigMigration.migrate(p).equals("NO_SUPPORTED_KEY"));
  p=fixture(run,"last-line",on,"render_enhanced_chests=true");check(ChestConfigMigration.migrate(p).equals("MIGRATED_WITH_BACKUP"));check(Files.readString(p.resolve("config/enhanced_bes.properties")).equals("render_enhanced_chests=false"));
  p=fixture(run,"indent",on,"  render_enhanced_chests = true # old default\nother=false\n");check(ChestConfigMigration.migrate(p).equals("MIGRATED_WITH_BACKUP"));check(Files.readString(p.resolve("config/enhanced_bes.properties")).equals("  render_enhanced_chests = false # old default\nother=false\n"));
  System.out.println("MIGRATION_TEST_PASS checks="+checks);
 }
}

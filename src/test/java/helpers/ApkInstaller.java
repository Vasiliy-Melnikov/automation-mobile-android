package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ApkInstaller {

    public static void reinstall(String udid, String appPackage, String apkPath) {
        String apkAbsolute = toAbsolutePath(apkPath);
        adbRunIgnoreExitCode(udid, "uninstall", appPackage);
        adbRun(udid, "install", "-r", "-g", apkAbsolute);
    }

    private static String toAbsolutePath(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("APK path is empty");
        }
        File f = new File(path);
        return f.isAbsolute() ? f.getAbsolutePath() : new File(System.getProperty("user.dir"), path).getAbsolutePath();
    }

    private static void adbRun(String udid, String... args) {
        int code = adbRunInternal(udid, false, args);
        if (code != 0) throw new RuntimeException("adb failed, exitCode=" + code);
    }

    private static void adbRunIgnoreExitCode(String udid, String... args) {
        adbRunInternal(udid, true, args);
    }

    private static int adbRunInternal(String udid, boolean ignoreExitCode, String... args) {
        try {
            String[] cmd = new String[args.length + 3];
            cmd[0] = "adb";
            cmd[1] = "-s";
            cmd[2] = udid;
            System.arraycopy(args, 0, cmd, 3, args.length);

            Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("[adb] " + line);
                }
            }

            int code = p.waitFor();
            if (!ignoreExitCode && code != 0) return code;
            return code;

        } catch (Exception e) {
            throw new RuntimeException("Failed to run adb command", e);
        }
    }
}

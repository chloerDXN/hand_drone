package epfl.handdrone.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Regroupe les méthodes liées à la demande et la vérification des permissions de l'application.
 * Ces méthodes permettent le fonctionnement de l'application pour l'API >= 23.
 */
public final class PermissionUtil {

    public static final String EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int EXTERNAL_STORAGE_REQUEST = 99;

    /**
     * Indique si l'application dispose des droits d'accès en lecture/écriture
     * au stockage externe de l'appareil.
     *
     * @return true si autorisé, false sinon
     */
    public static boolean hasExternalStoragePermission(@NonNull Context ctx) {
        return ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Demande la permission d'accès au stockage externe. Une boite de dialogue sera affichée.
     * Le résultat de la demande sera fourni par la méthode
     * {@link android.support.v7.app.AppCompatActivity#onRequestPermissionsResult(int, String[], int[])}.
     *
     * @param activity activité effectuant la demande de permission
     */
    public static void requestExternalStoragePermission(@NonNull Activity activity) {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(activity, permissions, EXTERNAL_STORAGE_REQUEST);
    }
}

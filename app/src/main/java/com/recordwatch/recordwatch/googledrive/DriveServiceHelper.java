package com.recordwatch.recordwatch.googledrive;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;


/**
 * A utility for performing read/write operations on Drive files via the REST API and opening a
 * file picker UI via Storage Access Framework.
 */
public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;

    public DriveServiceHelper(Drive driveService) {

        mDriveService = driveService;
    }

    public static Drive getGoogleDriveService(Context context, GoogleSignInAccount account, String appName) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton(DriveScopes.DRIVE_FILE));
        credential.setSelectedAccount(account.getAccount());
        Drive googleDriveService =
                new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        credential)
                        .setApplicationName(appName)
                        .build();
        return googleDriveService;
    }

    public Task<Void> deleteFolderFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Retrieve the metadata as a File object.
                if (fileId != null) {
                    mDriveService.files().delete(fileId).execute();
                }

                return null;
            }
        });
    }

    public Task<GoogleDriveFileHolder> uploadFile(final java.io.File localFile, final String mimeType, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                // Retrieve the metadata as a File object.

                List<String> root;
                if (folderId == null) {
                    root = Collections.singletonList("root");
                } else {

                    root = Collections.singletonList(folderId);
                }

                File metadata = new File()
                        .setParents(root)
                        .setMimeType(mimeType)
                        .setName(localFile.getName());

                FileContent fileContent = new FileContent(mimeType, localFile);

                File fileMeta = mDriveService.files().create(metadata, fileContent).execute();
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(fileMeta.getId());
                googleDriveFileHolder.setName(fileMeta.getName());
                return googleDriveFileHolder;
            }
        });
    }

    private static final String TAG = "DriveServiceHelper";

    public Task<List<GoogleDriveFileHolder>> searchFile(final String fileName, final String mimeType) {
        return Tasks.call(mExecutor, new Callable<List<GoogleDriveFileHolder>>() {
            @Override
            public List<GoogleDriveFileHolder> call() throws Exception {
                List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
                // Retrive the metadata as a File object.
                FileList result = mDriveService.files().list()
                        .setQ("name = '" + fileName + "' and mimeType ='" + mimeType + "'")
                        .setSpaces("drive")
                        .setFields("files(id, name,size,createdTime,modifiedTime,starred)")
                        .execute();

                if(!result.isEmpty()) {
                    for (int i = 0; i < result.getFiles().size(); i++) {
                        GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                        googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                        googleDriveFileHolder.setName(result.getFiles().get(i).getName());
                        googleDriveFileHolder.setModifiedTime(result.getFiles().get(i).getModifiedTime());
                        googleDriveFileHolder.setSize(result.getFiles().get(i).getSize());
                        googleDriveFileHolderList.add(googleDriveFileHolder);

                    }
                    if (!googleDriveFileHolderList.isEmpty()) {
                        return googleDriveFileHolderList;
                    }
                }
                return null;
            }
        });
    }

    public Task<Void> downloadFile(final java.io.File fileSaveLocation, final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Retrieve the metadata as a File object.
                OutputStream outputStream = new FileOutputStream(fileSaveLocation);
                mDriveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                return null;
            }
        });
    }

}
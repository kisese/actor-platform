package im.actor.core.api;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.runtime.bser.*;
import im.actor.runtime.collections.*;
import static im.actor.runtime.bser.Utils.*;
import im.actor.core.network.parser.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.google.j2objc.annotations.ObjectiveCName;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ApiDocumentMessage extends ApiMessage {

    private long fileId;
    private long accessHash;
    private int fileSize;
    private String name;
    private String mimeType;
    private ApiFastThumb thumb;
    private ApiDocumentEx ext;

    public ApiDocumentMessage(long fileId, long accessHash, int fileSize, @NotNull String name, @NotNull String mimeType, @Nullable ApiFastThumb thumb, @Nullable ApiDocumentEx ext) {
        this.fileId = fileId;
        this.accessHash = accessHash;
        this.fileSize = fileSize;
        this.name = name;
        this.mimeType = mimeType;
        this.thumb = thumb;
        this.ext = ext;
    }

    public ApiDocumentMessage() {

    }

    public int getHeader() {
        return 3;
    }

    public long getFileId() {
        return this.fileId;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getMimeType() {
        return this.mimeType;
    }

    @Nullable
    public ApiFastThumb getThumb() {
        return this.thumb;
    }

    @Nullable
    public ApiDocumentEx getExt() {
        return this.ext;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.fileId = values.getLong(1);
        this.accessHash = values.getLong(2);
        this.fileSize = values.getInt(3);
        this.name = values.getString(4);
        this.mimeType = values.getString(5);
        this.thumb = values.optObj(6, new ApiFastThumb());
        if (values.optBytes(8) != null) {
            this.ext = ApiDocumentEx.fromBytes(values.getBytes(8));
        }
        if (values.hasRemaining()) {
            setUnmappedObjects(values.buildRemaining());
        }
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        writer.writeLong(1, this.fileId);
        writer.writeLong(2, this.accessHash);
        writer.writeInt(3, this.fileSize);
        if (this.name == null) {
            throw new IOException();
        }
        writer.writeString(4, this.name);
        if (this.mimeType == null) {
            throw new IOException();
        }
        writer.writeString(5, this.mimeType);
        if (this.thumb != null) {
            writer.writeObject(6, this.thumb);
        }
        if (this.ext != null) {
            writer.writeBytes(8, this.ext.buildContainer());
        }
        if (this.getUnmappedObjects() != null) {
            SparseArray<Object> unmapped = this.getUnmappedObjects();
            for (int i = 0; i < unmapped.size(); i++) {
                int key = unmapped.keyAt(i);
                writer.writeUnmapped(key, unmapped.get(key));
            }
        }
    }

    @Override
    public String toString() {
        String res = "struct DocumentMessage{";
        res += "fileId=" + this.fileId;
        res += ", fileSize=" + this.fileSize;
        res += ", name=" + this.name;
        res += ", mimeType=" + this.mimeType;
        res += ", thumb=" + (this.thumb != null ? "set":"empty");
        res += ", ext=" + (this.ext != null ? "set":"empty");
        res += "}";
        return res;
    }

}
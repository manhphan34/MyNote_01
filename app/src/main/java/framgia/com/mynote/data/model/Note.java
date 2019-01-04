package framgia.com.mynote.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = Note.TABLE_NAME)
public class Note implements Parcelable {
    public static final String TABLE_NAME = "NOTE";
    public static final String ID = "ID";
    public static final String NOTE_TITLE = "NOTE_TITLE";
    public static final String NOTE_DES = "NOTE_DES";
    public static final String NOTE_TIME = "NOTE_TIME";
    public static final String NOTE_IMAGE = "NOTE_IMAGE";
    public static final String NOTE_AUDIO = "NOTE_AUDIO";
    public static final String NOTE_LOCATION = "NOTE_LOCATION";
    public static final String HAS_TASK = "HAS_TASK";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    private int mId;
    @ColumnInfo(name = NOTE_TITLE)
    private String mTitle;
    @ColumnInfo(name = NOTE_DES)
    private String mDescription;
    @ColumnInfo(name = NOTE_TIME)
    private long mTime;
    @ColumnInfo(name = NOTE_IMAGE)
    private String mImage;
    @ColumnInfo(name = NOTE_AUDIO)
    private String mAudio;
    @ColumnInfo(name = NOTE_LOCATION)
    private String mLocation;
    @ColumnInfo(name = HAS_TASK)
    private int mHasTask;

    public Note() {
    }

    @Ignore
    private Note(Builder builder) {
        mId = builder.mId;
        mTitle = builder.mTitle;
        mDescription = builder.mDescription;
        mTime = builder.mTime;
        mImage = builder.mImage;
        mAudio = builder.mAudio;
        mLocation = builder.mLocation;
        mHasTask = builder.mHasTask;
    }

    @Ignore
    protected Note(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mDescription = in.readString();
        mTime = in.readLong();
        mImage = in.readString();
        mAudio = in.readString();
        mLocation = in.readString();
        mHasTask = in.readInt();
    }


    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeLong(mTime);
        dest.writeString(mImage);
        dest.writeString(mAudio);
        dest.writeString(mLocation);
        dest.writeInt(mHasTask);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getAudio() {
        return mAudio;
    }

    public void setAudio(String audio) {
        mAudio = audio;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public int getHasTask() {
        return mHasTask;
    }

    public void setHasTask(int hasTask) {
        mHasTask = hasTask;
    }

    public static Creator<Note> getCREATOR() {
        return CREATOR;
    }

    public static class Builder {
        private int mId;
        private String mTitle;
        private String mDescription;
        private long mTime;
        private String mImage;
        private String mAudio;
        private String mLocation;
        private int mHasTask;

        public Builder() {
        }

        public Note build() {
            return new Note(this);
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public Builder setTime(long time) {
            mTime = time;
            return this;
        }

        public Builder setImage(String image) {
            mImage = image;
            return this;
        }

        public Builder setAudio(String audio) {
            mAudio = audio;
            return this;
        }

        public Builder setLocation(String location) {
            mLocation = location;
            return this;
        }

        public Builder setTask(int task) {
            mHasTask = task;
            return this;
        }
    }
}

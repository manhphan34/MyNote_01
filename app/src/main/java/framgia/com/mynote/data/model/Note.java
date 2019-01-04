package framgia.com.mynote.data.model;

public class Note {
    private int mId;
    private String mTitle;
    private String mDescription;
    private long mTime;
    private String mImage;
    private String mAudio;
    private String mLocation;
    private boolean mIsTask;

    public Note() {
    }

    private Note(Builder builder) {
        mId = builder.mId;
        mTitle = builder.mTitle;
        mDescription = builder.mDescription;
        mTime = builder.mTime;
        mImage = builder.mImage;
        mAudio = builder.mAudio;
        mLocation = builder.mLocation;
        mIsTask = builder.mIsTask;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public void setAudio(String audio) {
        mAudio = audio;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public void setTask(boolean task) {
        mIsTask = task;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public long getTime() {
        return mTime;
    }

    public String getImage() {
        return mImage;
    }

    public String getAudio() {
        return mAudio;
    }

    public String getLocation() {
        return mLocation;
    }

    public boolean getTask() {
        return mIsTask;
    }

    public static class Builder {
        private int mId;
        private String mTitle;
        private String mDescription;
        private long mTime;
        private String mImage;
        private String mAudio;
        private String mLocation;
        private boolean mIsTask;

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

        public Builder setTask(boolean task) {
            mIsTask = task;
            return this;
        }
    }
}

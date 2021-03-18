package com.example.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class UserParcelable implements Parcelable {

    private int age;
    private String name;
    private boolean isMale;

    private BookParcelable bookParcelable;

    public UserParcelable(int age, String name, boolean isMale) {
        this.age = age;
        this.name = name;
        this.isMale = isMale;
    }

    protected UserParcelable(Parcel in) {
        age = in.readInt();
        name = in.readString();
        isMale = in.readByte() != 0;
        /*bookParcelable是序列化对象，因此需要传递当前线程的类加载器*/
        bookParcelable = in.readParcelable(BookParcelable.class.getClassLoader());
    }

    /**
     * 反序列化由CREATOR实现。
     * 通过Parcel的read方法实现。
     * */
    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };

    /**
     * 对象中存在文件描述符时返回1，其他均返回0。
     * */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 封装了可序列化数据，可在Binder中传输。
     * 序列化最终由Parcel的write方法完成。
     * */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(bookParcelable, flags);
    }
}

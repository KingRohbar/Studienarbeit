package de.kingrohbar.leavethehouse

import android.os.Parcel
import android.os.Parcelable
import android.text.BoringLayout
import java.io.FileDescriptor

class Task(var title: String, var description: String?) : Parcelable {
    var checked: Boolean = false;

    constructor(title: String, description: String?, checked: Boolean): this(title, description){
        this.checked = checked
    }

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString()
    ) {
        checked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (checked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
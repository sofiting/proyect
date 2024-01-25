import android.os.Parcel
import android.os.Parcelable

class Travel(
    val name: String,
    val dni: String,
    val isRoundTrip: Boolean,
    val originTrip: String,
    val destiTrip: String,
    val numberPassenger: String,
    val pickDate: String,
    val pickTime: String,
    val backDate: String,
    val backTime: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() == 1,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(dni)
        parcel.writeInt(if (isRoundTrip) 1 else 0)
        parcel.writeString(originTrip)
        parcel.writeString(destiTrip)
        parcel.writeString(numberPassenger)
        parcel.writeString(pickDate)
        parcel.writeString(pickTime)
        parcel.writeString(backDate)
        parcel.writeString(backTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Travel> {
        override fun createFromParcel(parcel: Parcel): Travel {
            return Travel(parcel)
        }

        override fun newArray(size: Int): Array<Travel?> {
            return arrayOfNulls(size)
        }
    }
}

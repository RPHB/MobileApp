package tptest.test.esgi.com.beyoubet

import android.app.Application

class UserInformation : Application() {

    private var userId : Int = 0


    fun setUserId(id : Int)
    {
        userId = id
    }

    fun getUserId() : Int
    {
        return userId
    }

}
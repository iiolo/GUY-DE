package com.hifi.hifi_shopping.buy.buy_vm

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hifi.hifi_shopping.buy.buy_repository.OrderItemRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


data class ProductData(var idx: String, var name: String, var price: String, var context: String, var category: String, var pointAmount: String, var sellerIdx: String)

class OrderItemViewModel: ViewModel() {

    var productDataList = MutableLiveData<MutableList<ProductData>>()
    var tempProductList = mutableListOf<ProductData>()

    var productImgDataList = MutableLiveData<MutableList<Bitmap>>()
    var tempImgProductList = mutableListOf<Bitmap>()

    init{
        tempProductList.clear()
        tempImgProductList.clear()
        productDataList.value = tempProductList
        productImgDataList.value = tempImgProductList
    }

    fun getOrderProductData(idx: String){

        OrderItemRepository.getOrderProductData(idx, {
            for (i1 in it.result.children) {
                val temp = ProductData(
                    i1.child("idx").value as String,
                    i1.child("name").value as String,
                    i1.child("price").value as String,
                    i1.child("context").value as String,
                    i1.child("category").value as String,
                    i1.child("pointAmount").value as String,
                    i1.child("sellerIdx").value as String,
                )
                tempProductList.add(temp)
            }
            productDataList.value = tempProductList
        },{
            OrderItemRepository.getProductImgSrc(idx){
                for(c1 in it.result.children){
                    if(c1.child("default").value as String == "true"){
                        OrderItemRepository.getProductImg(c1.child("imgSrc").value as String){
                            thread {
                                // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
                                val url = URL(it.result.toString())
                                // 접속한다.
                                val httpURLConnection =
                                    url.openConnection() as HttpURLConnection
                                val bitmap =
                                    BitmapFactory.decodeStream(httpURLConnection.inputStream)
                                tempImgProductList.add(bitmap)
                                productImgDataList.postValue(tempImgProductList)
                            }
                        }
                    }
                }
            }

        })
    }

}
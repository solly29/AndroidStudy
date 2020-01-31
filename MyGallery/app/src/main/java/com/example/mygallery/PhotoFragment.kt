package com.example.mygallery


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_URI = "uri"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {
    private var uri: String? = null

    //Glide 라이브러리를 쓰는 이유는 미사용 리소스를 자동으로 해제하고
    //메모리를 효율적으로 쓴다. 그리고 이미지를 비동기로 로딩하여 UI의 끊김이 없다.
    //프래그먼트가 생성되면 호출한다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uri = it.getString(ARG_URI)
        }
    }

    //프래그먼트에 표시될 뷰를 생성한다.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false) //액티비티가 아닌곳에서 레이아웃 리소스를 가지고옴
    }

    //뷰가 완성된 직후에 호출한다.
    //savedInstanceState는 상태를 저장하는 객체이다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //with으로 사용할 준비를 하고, load로 해당 이미지를 부드럽게 로딩, into으로 해당 뷰에 표시한다.
        Glide.with(this).load(uri).into(imageView)
    }


    companion object {
        //프래그먼트를 생성한다.
        @JvmStatic
        fun newInstance(uri: String) =
            PhotoFragment().apply {
                //Bundle 는 여러가지의 타입의 값을 저장하는 map 클래스이다.
                arguments = Bundle().apply {
                    putString(ARG_URI, uri)
                }
            }
    }
}

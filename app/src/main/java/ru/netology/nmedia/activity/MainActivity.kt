package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.viewmodel.PostViewMode
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewMode by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
           insets
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likes.text = getShortSrt(post.likes)
                repost.text = getShortSrt(post.reposts)
                heart.setImageResource(R.drawable.ic_heart)
            }

            binding.heart.setOnClickListener {
                viewModel.like()
                binding.likes.text = getShortSrt(post.likes)
                binding.heart.setImageResource(
                    if (post.likeByMe) {
                        R.drawable.ic_red_heart
                    } else {
                        R.drawable.ic_heart
                    }
                )
            }

            binding.share.setOnClickListener {
                viewModel.repost()
                binding.repost.text = getShortSrt(post.reposts)
            }
        }


//        findViewById<ImageButton>(R.id.heart).setOnClickListener {
//            if (it !is ImageButton) {
//                return@setOnClickListener
//            }
//            (it as ImageButton).setImageResource(R.drawable.ic_red_heart)
//        }
    }

    private fun getShortSrt(n: Int): String {
        when {
            n < 1_000 -> return n.toString()
            (n < 10_000 && ((n / 100) - ((n / 1_000) * 10)) != 0) -> return (n / 1_000).toString() + "." + ((n / 100) - ((n / 1_000) * 10)).toString() + "К"
            n < 1_000_000 -> return ((n / 1_000).toString() + "K")
            (n < 10_000_000 && ((n / 100_000) - ((n / 1_000_000) * 10)) != 0) -> return (n / 1_000_000).toString() + "." + ((n / 100_000) - ((n / 1_000_000) * 10)).toString() + "M"
        }
        return ((n / 1_000_000).toString() + "M")
    }

}
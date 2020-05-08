package com.sujewan.dbs.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sujewan.dbs.databinding.ItemArticleBinding
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.view.ui.home.ItemArticleViewModel

class ArticlesAdapter(private val delegate: ArticlesAdapterDelegate) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var articleList = ArrayList<Article>()

    fun updateList(articles: List<Article>) {
        articleList = articles as ArrayList<Article>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleViewHolder).bind(articleList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    inner class ArticleViewHolder(private var binding: ItemArticleBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.vModel  = ItemArticleViewModel(article)
            binding.executePendingBindings()

            binding.cardViewArticle.setOnClickListener {
                delegate.onArticleSelected(article)
            }
        }
    }

    interface ArticlesAdapterDelegate {
        fun onArticleSelected(article: Article)
    }
}
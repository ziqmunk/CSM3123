package com.example.donationapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donationapp.R
import com.example.donationapp.model.Campaign
import java.text.DecimalFormat

class CampaignAdapter(
    private val campaigns: List<Campaign>,
    private val onItemClick: (Campaign) -> Unit // Lambda for item clicks
) : RecyclerView.Adapter<CampaignAdapter.CampaignViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_campaign, parent, false)
        return CampaignViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.bind(campaigns[position], onItemClick)
    }

    override fun getItemCount(): Int = campaigns.size

    class CampaignViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCampaignName: TextView = itemView.findViewById(R.id.tvCampaignName)
        private val tvCampaignAmount: TextView = itemView.findViewById(R.id.tvCampaignAmount)

        fun bind(campaign: Campaign, onItemClick: (Campaign) -> Unit) {
            tvCampaignName.text = campaign.campaignName
            // Format and display the amount with "RM"
            val formattedAmount = DecimalFormat("#,###.##").format(campaign.targetAmount)
            tvCampaignAmount.text = "RM $formattedAmount"


            // Set click listener for the item
            itemView.setOnClickListener {
                onItemClick(campaign)
            }
        }
    }
}

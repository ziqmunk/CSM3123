package com.example.donationapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donationapp.R
import com.example.donationapp.model.DonationWithCampaign

class DonationAdapter(
    private val donations: List<DonationWithCampaign>,
    private val onItemClick: (DonationWithCampaign) -> Unit // Callback for item click
) : RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    inner class DonationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCampaignName: TextView = itemView.findViewById(R.id.tvCampaignName)
        val tvDonationAmount: TextView = itemView.findViewById(R.id.tvDonationAmount)
        val tvDonationRemark: TextView = itemView.findViewById(R.id.tvDonationRemark)

        // Bind data to the views
        fun bind(donationWithCampaign: DonationWithCampaign) {
            tvCampaignName.text = donationWithCampaign.donation.campaignName // Access the campaign name via donation
            tvDonationAmount.text = "Amount: RM ${String.format("%.2f", donationWithCampaign.donation.donationAmount)}"
            tvDonationRemark.text = "Remark: ${donationWithCampaign.donation.remark}" // Access donation remark
            itemView.setOnClickListener {
                onItemClick(donationWithCampaign) // Trigger callback on click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_donation, parent, false)
        return DonationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        val donationWithCampaign = donations[position]
        holder.bind(donationWithCampaign) // Bind data to the view
    }

    override fun getItemCount(): Int = donations.size
}

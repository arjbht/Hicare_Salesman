package com.ab.hicaresalesman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.hicaresalesman.R;
import com.ab.hicaresalesman.databinding.ItemRecyclerSelectServiceAdapterBinding;
import com.ab.hicaresalesman.handler.OnListItemClickHandler;
import com.ab.hicaresalesman.network.models.pest_service.AddServiceRequest;
import com.ab.hicaresalesman.network.models.pest_service.ServiceData;
import com.ab.hicaresalesman.viewmodel.ServiceViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arjun Bhatt on 4/26/2021.
 */
public class RecycleSelectServiceAdapter extends RecyclerView.Adapter<RecycleSelectServiceAdapter.ViewHolder> {

    private OnListItemClickHandler onItemClickHandler;
    private final Context mContext;
    private static List<ServiceViewModel> items = null;
    private OnOptionClicked onOptionClicked;
    private ArrayList<String> multileItems = null;
    private HashMap<Integer, AddServiceRequest> mMap;
    private boolean isCostGenerated = false;

    public RecycleSelectServiceAdapter(Context context, HashMap<Integer, AddServiceRequest> mMap, boolean isCostGenerated, OnOptionClicked onOptionClicked) {
        if (items == null) {
            items = new ArrayList<>();
        }
        if (multileItems == null) {
            multileItems = new ArrayList<>();
        }
        this.mMap = mMap;
        this.onOptionClicked = onOptionClicked;
        this.mContext = context;
        this.isCostGenerated = isCostGenerated;
    }

    @NotNull
    @Override
    public RecycleSelectServiceAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemRecyclerSelectServiceAdapterBinding mItemRecyclerSelectServiceAdapterBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_recycler_select_service_adapter, parent, false);
        return new RecycleSelectServiceAdapter.ViewHolder(mItemRecyclerSelectServiceAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final RecycleSelectServiceAdapter.ViewHolder holder, final int position) {
        holder.mItemRecyclerSelectServiceAdapterBinding.txtServiceName.setText(items.get(position).getServiceName() + "(" + items.get(position).getServiceCode() + ")");
        holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setOnCheckedChangeListener(null);
        holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setChecked(false);

        if (isCostGenerated) {
            holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setEnabled(false);
            holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setClickable(false);
        } else {
            holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setEnabled(true);
            holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setClickable(true);
        }

        holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked/* && holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.isPressed()*/) {
                    onOptionClicked.onClicked(items.get(holder.getAdapterPosition()).getServiceId(), holder.getAdapterPosition(), true);
                    items.get(position).setSelected(true);
                } else {
                    onOptionClicked.onClicked(items.get(holder.getAdapterPosition()).getServiceId(), holder.getAdapterPosition(), false);
                    items.get(position).setSelected(false);
                }
            }
        });
        if (items.get(position).isSelected()) {
            holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setChecked(true);

        } else {
            holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setChecked(false);
        }
        items.get(position).setSelected(false);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickHandler(OnListItemClickHandler onItemClickHandler) {
        this.onItemClickHandler = onItemClickHandler;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull @NotNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setOnCheckedChangeListener(null);
        holder.mItemRecyclerSelectServiceAdapterBinding.checkOption.setChecked(false);
    }


    public void setData(List<ServiceData> data) {
        items.clear();
        for (int index = 0; index < data.size(); index++) {
            ServiceViewModel serviceViewModel = new ServiceViewModel();
            serviceViewModel.clone(data.get(index));
            items.add(serviceViewModel);
        }
    }

    public ServiceViewModel getItem(int position) {
        return items.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecyclerSelectServiceAdapterBinding mItemRecyclerSelectServiceAdapterBinding;

        public ViewHolder(ItemRecyclerSelectServiceAdapterBinding mItemRecyclerSelectServiceAdapterBinding) {
            super(mItemRecyclerSelectServiceAdapterBinding.getRoot());
            this.mItemRecyclerSelectServiceAdapterBinding = mItemRecyclerSelectServiceAdapterBinding;
        }
    }

    public interface OnOptionClicked {
        void onClicked(int serviceId, int position, boolean isChecked);
    }

}




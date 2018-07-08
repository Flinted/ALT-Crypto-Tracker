package makes.flint.alt.ui.constraintui.trackerList.trackerListAdapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import makes.flint.alt.R
import makes.flint.alt.configuration.IndicatorCustomiser
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.interfaces.assessChange
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.injection.components.PresenterComponent
import rx.subjects.PublishSubject

/**
 * TrackerListAdapter
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class TrackerListAdapter(presenterComponent: PresenterComponent) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    TrackerAdapterContractView, Filterable {

    // Properties

    override var trackerEntries: MutableList<TrackerListItem> = mutableListOf()
        set(value) {
            field = value
            filteredTrackerEntries = value
        }

    internal var filteredTrackerEntries = trackerEntries
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // RX Actions

    private var trackerEntrySelected: PublishSubject<TrackerListItem> = PublishSubject.create()
    private var noEntriesFound: PublishSubject<Boolean> = PublishSubject.create()
    private var isRefreshing: PublishSubject<Boolean> = PublishSubject.create()
    override fun onRefreshStateChange() = isRefreshing.asObservable()
    override fun onTrackerEntrySelected() = trackerEntrySelected.asObservable()
    override fun onNoEntriesPresent() = noEntriesFound.asObservable()

    private var trackerAdapterPresenter = presenterComponent.provideTrackerAdapterPresenter()
    private val indicatorCustomiser = IndicatorCustomiser(POHSettings.iconSet)

    // Lifecycle

    init {
        trackerAdapterPresenter.attachView(this)
        trackerAdapterPresenter.initialise()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_tracker_list, parent, false)
        return TrackerListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as TrackerListViewHolder
        val entry = filteredTrackerEntries[position]
        val status = entry.assessChange()
        val context = viewHolder.indicator.context
        viewHolder.coinName.text = entry.name
        viewHolder.coinSymbol.text = entry.symbolFormatted
        viewHolder.currentPrice.text = entry.getCurrentAssetPriceFormatted()
        viewHolder.dollarCostAverage.text = entry.dollarCostAverageFormatted
        viewHolder.currentProfit.text = entry.percentageChangeFormatted
        viewHolder.currentProfit.setTextColor(
            ContextCompat.getColor(
                context,
                indicatorCustomiser.getColor(
                    entry.assessChange()
                )
            )
        )
        setAmountValues( viewHolder, entry)
        viewHolder.indicator.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                indicatorCustomiser.getIcon(
                    status
                )
            )
        )
        setOnClickListener(viewHolder.itemContent, entry)
    }

    private fun setAmountValues(
        viewHolder: TrackerListViewHolder,
        entry: TrackerListItem
    ) {
        if (POHSettings.hiddenValues) {
            setHiddenValues(viewHolder)
            return
        }
        setActualValues( viewHolder, entry)
    }

    private fun setHiddenValues(viewHolder: TrackerListViewHolder) {
        val hiddenText = "HIDDEN"
        viewHolder.currentValue.text = hiddenText
        viewHolder.currentProfit.text = hiddenText
        viewHolder.numberOwned.text = hiddenText

    }

    private fun setActualValues(
        viewHolder: TrackerListViewHolder,
        entry: TrackerListItem
    ) {
        viewHolder.currentValue.text = entry.currentPriceUSDFormatted
        viewHolder.numberOwned.text = entry.numberOwnedFormatted
    }

    override fun onDestroy() {
        trackerAdapterPresenter.onDestroy()
    }

    // Overrides

    override fun getFilter(): Filter {
        val callback = makeTrackerFilterCallback()
        return TrackerFilter(trackerEntries, callback)
    }

    private fun makeTrackerFilterCallback(): TrackerFilterCallback {
        return object : TrackerFilterCallback {
            override fun publishResults(filteredList: MutableList<TrackerListItem>) {
                filteredTrackerEntries = filteredList
            }
        }
    }

    override fun getItemCount() = filteredTrackerEntries.size

    override fun showNoTrackerEntriesMessage() = noEntriesFound.onNext(true)

    override fun didHaveEntries() = noEntriesFound.onNext(false)

    override fun initialiseTrackerList() = trackerAdapterPresenter.initialise()

    override fun showLoading() = isRefreshing.onNext(true)

    override fun hideLoading() = isRefreshing.onNext(false)

    override fun showError(stringId: Int?) {}

    override fun filterFor(input: String) = filter.filter(input)

    // Private Functions

    private fun setOnClickListener(itemContent: CardView, entry: TrackerListItem) {
        itemContent.setOnClickListener {
            trackerEntrySelected.onNext(entry)
        }
    }
}

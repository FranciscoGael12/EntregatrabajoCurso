/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.plantdetail
// Importing necessary libraries and components for the Fragment and its UI
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.samples.apps.sunflower.theme.SunflowerTheme

// Fragment representing the plant detail screen.
class PlantDetailFragment : Fragment() {

    // Retrieve the arguments passed to the fragment (e.g., plantId)
    private val args: PlantDetailFragmentArgs by navArgs()

    // ViewModel for plant details, fetched using a factory method with the plantId
    private val plantDetailViewModel: PlantDetailViewModel by viewModels {
        InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), args.plantId)
    }

    // Override onCreateView to initialize the fragment's view and bind data.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate and bind the layout for the fragment
        val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
            inflater, R.layout.fragment_plant_detail, container, false
        ).apply {

            // Set up the Compose view within the fragment
            composeView.apply {
                // Dispose the composition when the view's lifecycle is destroyed
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                )
                // Set the Compose content for plant details using the ViewModel
                setContent {
                    SunflowerTheme {
                        PlantDetailDescription(plantDetailViewModel)
                    }
                }
            }

            // Bind the ViewModel to the fragment's layout
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            // Set a callback for adding a plant to the garden
            callback = object : Callback {
                override fun add(plant: Plant?) {
                    plant?.let {
                        // Hide the FloatingActionButton and add the plant to the garden
                        hideAppBarFab(fab)
                        plantDetailViewModel.addPlantToGarden()
                        // Show a Snackbar notifying the plant was added
                        Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }

            // Set Compose content for the plant details (again, seems redundant but might be intentional for different layout states)
            composeView.setContent {
                MaterialTheme {
                    PlantDetailDescription(plantDetailViewModel)
                }
            }

            // Variable to track whether the toolbar is shown or not during scrolling
            var isToolbarShown = false

            // Listen for scroll events to change the visibility of the toolbar
            plantDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // Determine if the toolbar should be shown based on scroll position
                    val shouldShowToolbar = scrollY > toolbar.height

                    // If the state of the toolbar visibility has changed, update it
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // If the toolbar is visible, apply an elevation effect
                        appbar.isActivated = shouldShowToolbar

                        // Enable or disable the title on the toolbar based on visibility
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            // Set up the navigation for the toolbar's back button
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            // Set up the menu items in the toolbar (e.g., share option)
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        // Trigger the share intent when share item is clicked
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }

        }
        // Indicate that the fragment has an options menu
        setHasOptionsMenu(true)

        // Return the root view of the fragment binding
        return binding.root
    }

    // Helper function to create a share intent for sharing the plant details
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        // Generate the share text using the plant's name if it exists
        val shareText = plantDetailViewModel.plant.value.let { plant ->
            if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_plant, plant.name)
            }
        }
        // Create a share intent using the ShareCompat library
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    // Function to hide the FloatingActionButton when it's clicked
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    // Callback interface for adding a plant
    interface Callback {
        fun add(plant: Plant?)
    }
}

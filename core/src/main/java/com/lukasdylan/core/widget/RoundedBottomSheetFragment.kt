package com.lukasdylan.core.widget

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lukasdylan.core.R

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id>
 * on 17/09/18
 */

open class RoundedBottomSheetFragment : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme).also {
        it.setCanceledOnTouchOutside(false)
    }

}
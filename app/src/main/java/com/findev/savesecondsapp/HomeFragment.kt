package com.findev.savesecondsapp

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.findev.savesecondsapp.databinding.FragmentHomeBinding
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val code = "*#334#"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.btnPay.setOnClickListener {
            dialMpesaUSSD()
            //dialHoverUSSD()
        }
        return binding.root
    }

    private fun dialHoverUSSD() {
       /*   *//*  val intent = Intent(applicationContext, MainActivity::class.java).apply {
                action = NOTIFICATION_ACTION
                data = deepLink
            }
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )*//*
            val notification = NotificationCompat.Builder(
                applicationContext,
                NOTIFICATION_CHANNEL
            ).apply {
                // ...
                setContentIntent(pendingIntent)
                // ...
            }.build()
            notificationManager.notify(
                NOTIFICATION_TAG,
                NOTIFICATION_ID,
                notification
            )


        val notification = NotificationCompat.Builder(
            applicationContext,
            NOTIFICATION_CHANNEL
        ).apply {
            // ...
            setContentIntent(pendingIntent)
            // ...
        }.build()*/

        val i = HoverParameters.Builder(requireContext())
            .request("f2751520")
            /*.extra(
                "step_variable_name",
                variable_value_as_string
            )*/ // Only if your action has variables
            .buildIntent()
 /*      val pendingIntent =  PendingIntent.getActivity(
            requireContext(),
            0,
            i,
            PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent.send()*/
         startActivityForResult(i, 0)
    }

    private fun dialMpesaUSSD() {
        startActivity(
            Intent(
                "android.intent.action.CALL", Uri
                    .parse(
                        "tel:"
                                + code.replace(
                            "#".toRegex(),
                            Uri.encode("#")
                        )
                    )
            )
        )
    }


}
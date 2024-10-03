package pronenko.ghinfo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import pronenko.ghinfo.domain.exchangeCodeForToken
import pronenko.ghinfo.ui.profile.ProfileScreenViewModel

class CallbackActivity : AppCompatActivity() {
    private val viewModel: ProfileScreenViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri: Uri? = intent.data
        val code = uri?.getQueryParameter("code")
        Log.d("StarResult", "code = $code")
        if (code != null) {
            exchangeCodeForToken(code, this, viewModel)
        }
    }
}

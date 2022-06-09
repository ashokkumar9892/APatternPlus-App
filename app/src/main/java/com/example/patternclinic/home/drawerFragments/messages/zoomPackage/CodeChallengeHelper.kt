package com.example.patternclinic.home.drawerFragments.messages.zoomPackage

import android.util.Base64

import java.security.MessageDigest
import java.security.SecureRandom


class CodeChallengeHelper {
    companion object {
        var verifier: String? = null
    }

    fun createCodeVerifier() {
        val secureRandom = SecureRandom()
        val code = ByteArray(32)
        secureRandom.nextBytes(code)
        verifier = Base64.encodeToString(code, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }
    fun createCodeChallenge(verifier: String): String {
        val bytes: ByteArray = verifier.toByteArray(Charsets.US_ASCII)
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest: ByteArray = md.digest()
        return Base64.encodeToString(digest, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }
}
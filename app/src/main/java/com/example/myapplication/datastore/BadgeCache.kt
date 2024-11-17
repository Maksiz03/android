package com.example.myapplication.datastore

class BadgeCache {
    var shouldShowBadge: Boolean = false
        private set

    fun updateBadgeState(isDefault: Boolean) {
        shouldShowBadge = !isDefault
    }
}
package com.tim.location

sealed class MapProvider {
    object Google : MapProvider()
    object Yandex : MapProvider()
}

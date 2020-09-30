package com.example.beije.ui.detail

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import java.util.*

class DetailScreenTest{

    private val detailScreenTest = DetailScreen()

    @Test
    fun `test conversion data in ITALY`() {

        val dataResponse = "Mon, 27 Jul 2020 00:00:00 +0000"
        val dataOutputIta = "LUN, 27 LUG 2020"
        val locale = Locale.ITALY

        assertThat(detailScreenTest.convertStringToData(dataResponse, locale).toUpperCase()).isEqualTo(dataOutputIta)
    }

    @Test
    fun `test conversion data in FRANCE`() {

        val dataResponse = "Mon, 27 Jul 2020 00:00:00 +0000"
        val dataOutputIta = "LUN., 27 JUIL. 2020"
        val locale = Locale.FRANCE

        assertThat(detailScreenTest.convertStringToData(dataResponse, locale).toUpperCase()).isEqualTo(dataOutputIta)
    }

    @Test
    fun `test conversion data in US`() {

        val dataResponse = "Mon, 27 Jul 2020 00:00:00 +0000"
        val dataOutputIta = "MON, 27 JUL 2020"
        val locale = Locale.US

        assertThat(detailScreenTest.convertStringToData(dataResponse, locale).toUpperCase()).isEqualTo(dataOutputIta)
    }

    @Test
    fun `test conversion data in GERMANY`() {

        val dataResponse = "Mon, 27 Jul 2020 00:00:00 +0000"
        val dataOutputIta = "MO, 27 JUL 2020"
        val locale = Locale.GERMANY

        assertThat(detailScreenTest.convertStringToData(dataResponse, locale).toUpperCase()).isEqualTo(dataOutputIta)
    }

    @Test
    fun `test conversion data in JAPAN`() {

        val dataResponse = "Mon, 27 Jul 2020 00:00:00 +0000"
        val dataOutputIta = "月, 27 7 2020"
        val locale = Locale.JAPAN

        assertThat(detailScreenTest.convertStringToData(dataResponse, locale).toUpperCase()).isEqualTo(dataOutputIta)
    }
}

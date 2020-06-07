package com.elevenetc.android.news

import com.elevenetc.android.news.core.utils.updateRange
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestUtils {
    @Test
    fun updateRange() {
        var target = mutableListOf(0, 1, 2, 3, 4, 5)
        updateRange(target, listOf(-1, -2, -3), 0, 3)
        assertThat(target).isEqualTo(listOf(-1, -2, -3, 3, 4, 5))

        target = mutableListOf(0, 1, 2, 3, 4, 5)
        updateRange(target, listOf(-1, -1, -1), 1, 3)
        assertThat(target).isEqualTo(listOf(0, 1, 2, -1, -1, -1))

        target = mutableListOf(0, 1, 2, 3, 4)
        updateRange(target, listOf(-1, -1, -1), 1, 3)
        assertThat(target).isEqualTo(listOf(0, 1, 2, -1, -1))

        target = mutableListOf(0, 1, 2, 3, 4, 5)
        updateRange(target, listOf(-1, -1, -1, -1), 1, 3)
        assertThat(target).isEqualTo(listOf(0, 1, 2, -1, -1, -1))

        target = mutableListOf(0, 1, 2, 3, 4, 5)
        updateRange(target, listOf(-1, -1), 1, 3)
        assertThat(target).isEqualTo(listOf(0, 1, 2, -1, -1, 5))
    }

}
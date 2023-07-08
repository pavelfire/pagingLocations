package com.example.android.codelabs.paging.experimental.components

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.core.database.getStringOrNull
import androidx.lifecycle.ViewModel

// ТЗ https://horse-capri-dbe.notion.site/Surf-Android-Summer-School-2022-a6c7cfbf99624044b758103888416e61
// https://github.com/cetvs/SurfSummerSchool-with-fragments.git

class ServiceActivityViewModel : ViewModel() {

//    /** Возвращает список [SimilarContactsPair], взятый из ContentProvider'a. */
//    fun getContactsInfo(contentResolver: ContentResolver): List<SimilarContactsPair> {
//        val contactQuery = contentResolver.query(
//            ContactsContract.Contacts.CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        ) ?: return emptyList()
//        val parsedContacts: List<ContactInfo> = parseData(contentResolver, contactsQuery)
//        contactQuery.close()
//        return groupsBySimilar(parsedContacts)
//    }
//
//    /** Формирует из таблицы базы данных ContentProvider список [контактов][ContactInfo] */
//    private fun parseData(
//        contentResolver: ContentResolver,
//        cursor: Cursor,
//    ): List<ContactInfo> {
//        val contacts = mutableListOf<ContactInfo>()
//        while (cursor.moveToNext()) {
//            val id = cursor.getStringByColumn(ContactInfo.FIELD_ID)
//            val contactName = cursor.getStringByColumn(ContactInfo.)
//        }
//    }
//
//    /** Возвращает список пересекающихся контактов */
//    private fun getSimilarPhones(source: ContactInfo, target: ContactInfo): Set<String> =
//        source.phones?.intersect(target.phones?.toSet().orEmpty()).orEmpty()
//
//    /** Фильтрует одинаковые номера после форматирования в формат 7хххххххххх */
//    private fun filterSimilarPhones(phones: List<String>?): List<String> =
//        phones?.map {
//            val digitsOnly = it.replace("\\D+".toRegex(), "")
//            if (digitsOnly.length == PhoneUtils.LENGTH_PHONE_WITH_CODE
//                && digitsOnly.startsWith("8")
//            ) {
//                digitsOnly.replaceFirst("8", "7")
//            } else {
//                digitsOnly
//            }
//        }
//            ?.distinct()
//            .orEmpty()
//
//    /** Заменяет вызов двух методов */
//    private fun Cursor.getStringByColumn(column: String): String? {
//        return getStringOrNull(getColumnIndex(column))
//    }
}
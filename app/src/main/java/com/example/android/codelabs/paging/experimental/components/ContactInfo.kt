package com.example.android.codelabs.paging.experimental.components

import android.os.Parcelable
import android.provider.ContactsContract
import kotlinx.parcelize.Parcelize

/** Информация контакта.
 *
 * Наследуется от Parcelable, чтобы была возможность передавать объект между экранами.
 *
 * Аннотация [Parcelize] необходима для тогоб чтобы автоматически для каждого параметра
 * было сгенерировано добавление в [Parcelable] объект.
 * См. [документацию](https://developer.android.com/kotlin/parcelize)
 *
 * @param id id в таблице базы данных
 * @param name имя объекта
 * @param photoInfo фотография контакта
 * @param phones список телефонных номеров
 * @property FIELD_ID необходим для получения столбца [id] из [android.database.Cursor]
 * @property FIELD_NAME необходим для получения столбца [name] из [android.database.Cursor]
 * @property FIELD_PHONE необходим для получения столбца [phones] из [android.database.Cursor]
 */
@Parcelize
data class ContactInfo(
    val id: Long,
    val name: String?,
    val photoInfo: String,//PhotoInfo?,
    val phones: List<String>?
): Parcelable {

    companion object{
        const val FIELD_ID = ContactsContract.Contacts._ID
        const val FIELD_NAME = ContactsContract.Contacts.DISPLAY_NAME
        const val FIELD_PHONE = ContactsContract.CommonDataKinds.Phone.NUMBER
    }
}

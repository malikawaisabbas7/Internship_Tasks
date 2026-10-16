
package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model.ContactModel
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = ContactsRepository(application)

    private val _contacts = MutableLiveData<List<ContactModel>>()
    val contacts: LiveData<List<ContactModel>> = _contacts

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadContacts() {
        viewModelScope.launch {
            try {
                val contactList = withContext(Dispatchers.IO) {
                    repository.getContacts()
                }

                _contacts.value = contactList
                _errorMessage.value = null

            } catch (exception: SecurityException) {
                _errorMessage.value =
                    "Contacts permission is required to view contacts."

            } catch (exception: Exception) {
                _errorMessage.value =
                    "Unable to load contacts. Please try again."
            }
        }
    }

    fun toggleFavourite(contact: ContactModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.toggleFavourite(contact)
                }

                loadContacts()

            } catch (exception: SecurityException) {
                _errorMessage.value =
                    "Permission is required to update favourites."

            } catch (exception: Exception) {
                _errorMessage.value =
                    "Unable to update favourite status."
            }
        }
    }
}

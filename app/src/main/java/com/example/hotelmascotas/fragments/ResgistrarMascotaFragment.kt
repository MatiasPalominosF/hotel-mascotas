@file:Suppress("DEPRECATION")

package com.example.hotelmascotas.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelmascotas.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_resgistrar_mascota.*
import java.io.ByteArrayOutputStream


class ResgistrarMascotaFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private val mascotas = hashMapOf<String?, Any?>()
    private lateinit var progressDialog: ProgressDialog
    private lateinit var imageView: ImageView
    private var REQUEST_IMAGE_CAPTURE: Int = 101

    private lateinit var filePath: Uri
    lateinit var storage: FirebaseStorage
    private var PICK_IMAGE_REQUEST: Int = 234


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resgistrar_mascota, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = Firebase.storage

        imageView = imagen_camara_mascota
        db = FirebaseFirestore.getInstance()
        includesForCreateReference()

        button_camera.setOnClickListener {
            takePicture(view)
        }

        button_galery.setOnClickListener {

        }

        buttonAccept.setOnClickListener {
            registrarMascota()
            //uploadFile()
        }
    }

    private fun includesForCreateReference() {
        val storage = Firebase.storage

        // ## Create a Reference

        // [START create_storage_reference]
        // Create a storage reference from our app
        var storageRef = storage.reference
        // [END create_storage_reference]

        // [START create_child_reference]
        // Create a child reference
        // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("images")

        // Child references can also take paths
        // spaceRef now points to "images/space.jpg
        // imagesRef still points to "images"
        var spaceRef = storageRef.child("images/space.jpg")
        // [END create_child_reference]

        // ## Navigate with References

        // [START navigate_references]
        // parent allows us to move our reference to a parent node
        // imagesRef now points to 'images'
        imagesRef = spaceRef.parent

        // root allows us to move all the way back to the top of our bucket
        // rootRef now points to the root
        val rootRef = spaceRef.root
        // [END navigate_references]

        // [START chain_navigation]
        // References can be chained together multiple times
        // earthRef points to 'images/earth.jpg'
        val earthRef = spaceRef.parent?.child("earth.jpg")

        // nullRef is null, since the parent of root is null
        val nullRef = spaceRef.root.parent
        // [END chain_navigation]

        // ## Reference Properties

        // [START reference_properties]
        // Reference's path is: "images/space.jpg"
        // This is analogous to a file path on disk
        spaceRef.path

        // Reference's name is the last segment of the full path: "space.jpg"
        // This is analogous to the file name
        spaceRef.name

        // Reference's bucket is the name of the storage bucket that the files are stored in
        spaceRef.bucket
        // [END reference_properties]

        // ## Full Example

        // [START reference_full_example]
        // Points to the root reference
        storageRef = storage.reference

        // Points to "images"
        imagesRef = storageRef.child("images")

        // Points to "images/space.jpg"
        // Note that you can use variables to create child values
        val fileName = "space.jpg"
        spaceRef = imagesRef.child(fileName)

        // File path is "images/space.jpg"
        val path = spaceRef.path

        // File name is "space.jpg"
        val name = spaceRef.name

        // Points to "images"
        imagesRef = spaceRef.parent
        // [END reference_full_example]
    }


    private fun takePicture(view: View) {
        var imageTakeIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (imageTakeIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val storageRef = storage.reference
        val mountainsRef = storageRef.child("mascota.jpg")
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            //filePath = data?.data!!
            val extras: Bundle = data?.extras!!
            val imageBitmap: Bitmap = extras.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainsRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
            // [END upload_memory]
        }
    }

    fun registrarMascota() {
        val nombre: String = nombre_mascota.text.toString()
        val raza: String = raza_mascota.text.toString()
        val imagen: String =
            "https://image.freepik.com/vector-gratis/dibujos-animados-mascota-perro-lindo_97365-539.jpg"
        mascotas.put("foto", imagen)
        mascotas.put("nombre", nombre)
        mascotas.put("raza", raza)

        this.progressDialog = ProgressDialog(requireContext())
        this.progressDialog.show()
        this.progressDialog.setContentView(R.layout.progress_dialog)
        this.progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        db.collection("pets")
            .add(mascotas)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Mascota agregada correctamente",
                    Toast.LENGTH_LONG
                ).show()
                this.progressDialog.dismiss()
                findNavController().navigate(R.id.misMascotasFragment)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Error ${it.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }

    }



}
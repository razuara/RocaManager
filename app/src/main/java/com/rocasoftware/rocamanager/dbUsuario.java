package com.rocasoftware.rocamanager;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class dbUsuario
{
    private DatabaseReference databaseReference;
    public dbUsuario()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(usuario.class.getSimpleName());
    }

    public Task<Void> add(usuario usuario)
    {
        return databaseReference.push().setValue(usuario);
    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }
}

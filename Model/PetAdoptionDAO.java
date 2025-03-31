package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PetAdoptionDAO {
    public boolean addPet(PetAdoption pet) {
        String sql = "INSERT INTO PetAdoption (name, species, race, age, birthDate, gender, description, photo, adopted, entryDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, false, NOW())";
        
        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pet.getName());
            stmt.setString(2, pet.getSpecies());
            stmt.setString(3, pet.getRace());
            stmt.setInt(4, pet.getAge());

            if (pet.getBirthDate() != null) {
                stmt.setDate(5, new java.sql.Date(pet.getBirthDate().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setString(6, pet.getGender());
            stmt.setString(7, pet.getDescription());
            stmt.setString(8, pet.getPhoto());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PetAdoption> getAllPets() {
        List<PetAdoption> pets = new ArrayList<>();
        String sql = "SELECT * FROM PetAdoption WHERE adopted = false"; 
        
        try (Connection conn = BBDD_Connection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PetAdoption pet = new PetAdoption(
                    rs.getString("name"),
                    rs.getString("species"),
                    rs.getString("race"),
                    rs.getInt("age"),
                    rs.getDate("birthDate"),
                    rs.getString("gender"),
                    rs.getString("description"),
                    rs.getString("photo")
                );
                pet.setId(rs.getInt("id"));
                pet.setEntryDate(rs.getDate("entryDate"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }
}

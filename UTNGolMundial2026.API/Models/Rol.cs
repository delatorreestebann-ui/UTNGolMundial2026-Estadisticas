using System.ComponentModel.DataAnnotations;

namespace UTNGolMundial2026.API.Models
{
    public class Rol
    {
        [Key]
        public int Id { get; set; }

        [Required]
        [MaxLength(50)]
        public string Nombre { get; set; }

        [MaxLength(200)]
        public string? Descripcion { get; set; }

        public List<Usuario>? Usuarios { get; set; }
    }
}

using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace UTNGolMundial2026.API.Models
{
    public class Grupo
    {
        [Key]
        public int Id { get; set; }

        [Required]
        [MaxLength(20)]
        public string Nombre { get; set; }

        public List<Seleccion>? Selecciones { get; set; }
    }
}

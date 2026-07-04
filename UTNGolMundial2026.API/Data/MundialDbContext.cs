using Microsoft.EntityFrameworkCore;
using UTNGolMundial2026.API.Models;

namespace UTNGolMundial2026.API.Data
{
    public class MundialDbContext : DbContext
    {
        public MundialDbContext(DbContextOptions<MundialDbContext> options) : base(options)
        {
        }

        public DbSet<Rol> Roles { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Seleccion> Selecciones { get; set; }
        public DbSet<Grupo> Grupos { get; set; }
        public DbSet<Sede> Sedes { get; set; }
        public DbSet<Partido> Partidos { get; set; }
        public DbSet<Auditoria> Auditorias { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Usuario>()
                .HasIndex(u => u.Correo)
                .IsUnique();

            modelBuilder.Entity<Partido>()
                .HasOne(p => p.SeleccionLocal)
                .WithMany()
                .HasForeignKey(p => p.SeleccionLocalId)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<Partido>()
                .HasOne(p => p.SeleccionVisitante)
                .WithMany()
                .HasForeignKey(p => p.SeleccionVisitanteId)
                .OnDelete(DeleteBehavior.Restrict);
        }
    }
}

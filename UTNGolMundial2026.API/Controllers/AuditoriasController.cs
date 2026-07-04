using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using UTNGolMundial2026.API.Data;
using UTNGolMundial2026.API.Models;

namespace UTNGolMundial2026.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuditoriasController : ControllerBase
    {
        private readonly MundialDbContext _context;

        public AuditoriasController(MundialDbContext context)
        {
            _context = context;
        }

        // GET: api/Auditorias
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Auditoria>>> GetAuditorias()
        {
            return await _context.Auditorias.Include(a => a.Usuario).ToListAsync();
        }

        // GET: api/Auditorias/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Auditoria>> GetAuditoria(int id)
        {
            var auditoria = await _context.Auditorias.Include(a => a.Usuario).FirstOrDefaultAsync(a => a.Id == id);

            if (auditoria == null)
            {
                return NotFound();
            }

            return auditoria;
        }

        // POST: api/Auditorias
        [HttpPost]
        public async Task<ActionResult<Auditoria>> PostAuditoria(Auditoria auditoria)
        {
            // Asegurar fecha en UTC
            if (auditoria.FechaHora == default)
            {
                auditoria.FechaHora = DateTime.UtcNow;
            }
            else
            {
                auditoria.FechaHora = DateTime.SpecifyKind(auditoria.FechaHora, DateTimeKind.Utc);
            }

            _context.Auditorias.Add(auditoria);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetAuditoria", new { id = auditoria.Id }, auditoria);
        }

        // DELETE: api/Auditorias/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteAuditoria(int id)
        {
            var auditoria = await _context.Auditorias.FindAsync(id);
            if (auditoria == null)
            {
                return NotFound();
            }

            _context.Auditorias.Remove(auditoria);
            await _context.SaveChangesAsync();

            return NoContent();
        }
        
        
    }
}

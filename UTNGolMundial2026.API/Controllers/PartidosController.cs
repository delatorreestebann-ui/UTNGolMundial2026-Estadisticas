using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using UTNGolMundial2026.API.Data;
using UTNGolMundial2026.API.Models;

namespace UTNGolMundial2026.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PartidosController : ControllerBase
    {
        private readonly MundialDbContext _context;

        public PartidosController(MundialDbContext context)
        {
            _context = context;
        }

        // GET: api/Partidos
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Partido>>> GetPartidos()
        {
            return await _context.Partidos
                .Include(p => p.SeleccionLocal)
                .Include(p => p.SeleccionVisitante)
                .Include(p => p.Sede)
                .ToListAsync();
        }

        // GET: api/Partidos/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Partido>> GetPartido(int id)
        {
            var partido = await _context.Partidos
                .Include(p => p.SeleccionLocal)
                .Include(p => p.SeleccionVisitante)
                .Include(p => p.Sede)
                .FirstOrDefaultAsync(p => p.Id == id);

            if (partido == null)
            {
                return NotFound();
            }

            return partido;
        }

        // PUT: api/Partidos/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutPartido(int id, Partido partido)
        {
            if (id != partido.Id)
            {
                return BadRequest();
            }

            // para ver que un partido no sea contra el mismo equipo
            if (partido.SeleccionLocalId == partido.SeleccionVisitanteId)
            {
                return BadRequest("Una selección no puede jugar contra sí misma.");
            }

            partido.FechaHora = DateTime.SpecifyKind(partido.FechaHora, DateTimeKind.Utc);

            _context.Entry(partido).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PartidoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Partidos
        [HttpPost]
        public async Task<ActionResult<Partido>> PostPartido(Partido partido)
        {
            // parar que el partido no sea contra el mismo equipo
            if (partido.SeleccionLocalId == partido.SeleccionVisitanteId)
            {
                return BadRequest("Una selección no puede jugar contra sí misma.");
            }
            if (partido.FechaHora == default)
            {
                partido.FechaHora = DateTime.UtcNow;
            }
            else
            {
                partido.FechaHora = DateTime.SpecifyKind(partido.FechaHora, DateTimeKind.Utc);
            }

            _context.Partidos.Add(partido);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPartido", new { id = partido.Id }, partido);
        }

        // DELETE: api/Partidos/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePartido(int id)
        {
            var partido = await _context.Partidos.FindAsync(id);
            if (partido == null)
            {
                return NotFound();
            }

            _context.Partidos.Remove(partido);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool PartidoExists(int id)
        {
            return _context.Partidos.Any(e => e.Id == id);
        }
    }
}

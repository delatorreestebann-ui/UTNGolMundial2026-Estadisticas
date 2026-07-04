using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using UTNGolMundial2026.API.Data;
using UTNGolMundial2026.API.Models;

namespace UTNGolMundial2026.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SedesController : ControllerBase
    {
        private readonly MundialDbContext _context;

        public SedesController(MundialDbContext context)
        {
            _context = context;
        }

        // GET: api/Sedes
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Sede>>> GetSedes()
        {
            return await _context.Sedes.ToListAsync();
        }

        // GET: api/Sedes/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Sede>> GetSede(int id)
        {
            var sede = await _context.Sedes.FindAsync(id);

            if (sede == null)
            {
                return NotFound();
            }

            return sede;
        }

        // PUT: api/Sedes/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutSede(int id, Sede sede)
        {
            if (id != sede.Id)
            {
                return BadRequest();
            }

            _context.Entry(sede).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SedeExists(id))
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

        // POST: api/Sedes
        [HttpPost]
        public async Task<ActionResult<Sede>> PostSede(Sede sede)
        {
            _context.Sedes.Add(sede);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetSede", new { id = sede.Id }, sede);
        }

        // DELETE: api/Sedes/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSede(int id)
        {
            var sede = await _context.Sedes.FindAsync(id);
            if (sede == null)
            {
                return NotFound();
            }

            _context.Sedes.Remove(sede);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool SedeExists(int id)
        {
            return _context.Sedes.Any(e => e.Id == id);
        }
    }
}

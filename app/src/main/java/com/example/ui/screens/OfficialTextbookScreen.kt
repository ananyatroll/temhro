package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdsManager
import com.example.ui.StudyViewModel
import com.example.ui.components.*
import com.example.ui.theme.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class TextbookUnit(
    val unitNumber: String,
    val title: String,
    val pageStart: Int,
    val summary: String,
    val keyTopics: List<String>
)

data class TextbookEdition(
    val grade: String,
    val title: String,
    val fileName: String,
    val pageCount: Int,
    val publisher: String = "Ministry of Education Ethiopia (MoE)",
    val curriculumVersion: String = "New Curriculum (FDRE-MoE)",
    val downloadUrl: String? = null,
    val units: List<TextbookUnit>
)

object OfficialBookLinks {
    val urls = mapOf(
        // Grade 9
        "Grade_9_Mathematics.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-mathematics-new-curriculum--student-textbook-_kehulum_com_0fa6.pdf",
        "Grade_9_English.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-english-for-ethiopia-new-curriculum--student-textbook-kehulumcom175993262346a1.pdf",
        "Grade_9_Physics.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-physics-new-curriculum--student-textbook-kehulumcom1759930084a307.pdf",
        "Grade_9_Biology.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-biology-new-curriculum--student-textbook-kehulumcom17599334842417.pdf",
        "Grade_9_Chemistry.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-chemistry-new-curriculum--student-textbook-kehulumcom17599332397bc8.pdf",
        "Grade_9_History.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-history-new-curriculum--student-textbook-kehulumcom1759931286d445.pdf",
        "Grade_9_Geography.pdf" to "https://kehulum.com/books_asset/books_91/collection/grade-9-geography-new-curriculum--student-textbook-kehulumcom1759931498f683.pdf",
        "Grade_9_Economics.pdf" to "https://kehulum.com/bfile_asset/books_91/collection/grade-9-economics-new-curriculum--student-textbook-kehulumcom1759932804ce76.pdf",

        // Grade 10
        "Grade_10_Mathematics.pdf" to "https://kehulum.com/bfile_asset/books_92/collection/grade-10-mathematics-new-curriculum--student-textbook-kehulumcom1759925836e39e.pdf",
        "Grade_10_English.pdf" to "https://kehulum.com/bfile_asset/books_92/collection/grade-10-english-for-ethiopia-new-curriculum--student-textbook-kehulumcom1759928736fa82.pdf",
        "Grade_10_Physics.pdf" to "https://kehulum.com/bfile_asset/books_92/collection/grade-10-physics-new-curriculum--student-textbook-kehulumcom17599255229e06.pdf",
        "Grade_10_Biology.pdf" to "https://kehulum.com/books_asset/books_92/collection/grade%2010-biology_kehulumcom_d02c.pdf",
        "Grade_10_Chemistry.pdf" to "https://kehulum.com/books_asset/books_92/collection/grade-10-chemistry-new-curriculum--student-textbook-kehulumcom175992936186a3.pdf",
        "Grade_10_History.pdf" to "https://kehulum.com/bfile_asset/books_92/collection/grade-10-history-new-curriculum--student-textbook-kehulumcom1759927358e997.pdf",
        "Grade_10_Geography.pdf" to "https://kehulum.com/bfile_asset/books_92/collection/grade-10-geography-new-curriculum--student-textbook-kehulumcom17599285581298.pdf",
        "Grade_10_Economics.pdf" to "https://kehulum.com/bfile_asset/books_92/collection/grade-10-economics-new-curriculum--student-textbook-kehulumcom1759928992f5b9.pdf",

        // Grade 11
        "Grade_11_Mathematics.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-mathematics-new-curriculum--student-textbook-kehulumcom1759919088fbdb.pdf",
        "Grade_11_English.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-english-for-ethiopia-new-curriculum--student-textbook-kehulumcom175992307047b8.pdf",
        "Grade_11_Physics.pdf" to "https://kehulum.com/books_asset/books_98/collection/grade-11-physics-new-curriculum--student-textbook-kehulumcom17551049158ab7.pdf",
        "Grade_11_Biology.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-biology-new-curriculum--student-textbook-kehulumcom1759924577039e.pdf",
        "Grade_11_Chemistry.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-chemistry-new-curriculum--student-textbook-kehulumcom17599238964126.pdf",
        "Grade_11_History.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-history-new-curriculum--student-textbook-kehulumcom17599198807fa3.pdf",
        "Grade_11_Geography.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-geography-new-curriculum--student-textbook-kehulumcom1759920987ac44.pdf",
        "Grade_11_Economics.pdf" to "https://kehulum.com/bfile_asset/books_98/collection/grade-11-economics-new-curriculum--student-textbook-kehulumcom17599236035876.pdf",

        // Grade 12
        "Grade_12_Mathematics.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-mathematics-new-curriculum--student-textbook-kehulumcom17599122086bb1.pdf",
        "Grade_12_English.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-english-for-ethiopia-new-curriculum--student-textbook-kehulumcom1759915593b3b5.pdf",
        "Grade_12_Physics.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-physics-new-curriculum--student-textbook-kehulumcom1759910068aff6.pdf",
        "Grade_12_Biology.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-biology-new-curriculum--student-textbook-kehulumcom17599148324a02.pdf",
        "Grade_12_Chemistry.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-chemistry-new-curriculum--student-textbook-kehulumcom1759915918b6bd.pdf",
        "Grade_12_History.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-history-new-curriculum--student-textbook-kehulumcom17599097897dd4.pdf",
        "Grade_12_Geography.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-geography-new-curriculum--student-textbook-kehulumcom17599134797b34.pdf",
        "Grade_12_Economics.pdf" to "https://kehulum.com/bfile_asset/books_99/collection/grade-12-economics-new-curriculum--student-textbook-kehulumcom1759915712056d.pdf"
    )
}

object OfficialTextbookRegistry {
    fun getTextbooksForSubject(subjectName: String): List<TextbookEdition> {
        val lower = subjectName.lowercase()
        return when {
            lower.contains("bio") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Biology Student Textbook",
                    fileName = "Grade_12_Biology.pdf",
                    pageCount = 358,
                    units = listOf(
                        TextbookUnit("Unit 1", "Application of Biology", 1, "Biotechnology, microbial genetics, recombinant DNA, industrial fermentation, and bioethics in Ethiopia.", listOf("Recombinant DNA", "Biotechnology in Agriculture", "Fermentation & Bioethics")),
                        TextbookUnit("Unit 2", "Microorganisms", 54, "Classification of bacteria, viruses, fungi, protozoa, microbiological techniques, and microbial diseases.", listOf("Bacteria & Viruses", "Culturing Techniques", "Pathogenic Microbes")),
                        TextbookUnit("Unit 3", "Energy Transformation", 112, "Photosynthesis light/dark reactions, cellular respiration, glycolysis, Krebs cycle, and ATP synthesis.", listOf("Light-dependent Reactions", "Calvin Cycle", "Cellular Respiration & ATP")),
                        TextbookUnit("Unit 4", "Evolution", 180, "Theories of origin of life, Lamarckism, Darwinian natural selection, speciation, and hominid evolution in Ethiopia (Lucy/Ardi).", listOf("Darwinian Natural Selection", "Speciation", "Human Evolution in Ethiopia")),
                        TextbookUnit("Unit 5", "Human Body Systems & Health", 240, "Nervous coordination, endocrine glands, homeostasis, thermoregulation, and excretion mechanisms.", listOf("Action Potentials & Synapses", "Endocrine Hormones", "Homeostasis & Kidney Function"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Biology Student Textbook",
                    fileName = "Grade_11_Biology.pdf",
                    pageCount = 294,
                    units = listOf(
                        TextbookUnit("Unit 1", "Biology and Technology", 1, "Biomimicry, nature-inspired technologies, biotechnology tools, and bioinformatics fundamentals.", listOf("Learning from Nature", "Biomimicry Examples", "Modern Bio-tools")),
                        TextbookUnit("Unit 2", "Cell Biology and Enzymology", 42, "Cell membrane dynamics, organelle functions, enzyme kinetics, and metabolic pathways.", listOf("Fluid Mosaic Model", "Enzyme Activation Energy", "Allosteric Regulation")),
                        TextbookUnit("Unit 3", "Plant Biology", 102, "Plant tissues, xylem/phloem transport mechanisms, transpiration pull, and plant hormones.", listOf("Water Transport & Stomata", "Phloem Translocation", "Auxins & Gibberellins")),
                        TextbookUnit("Unit 4", "Genetics & Inheritance", 146, "Mendelian inheritance, sex linkage, blood group genetics, pedigree analysis, and gene therapy.", listOf("Monohybrid & Dihybrid", "Sex-linked Traits", "Pedigree Analysis & Gene Therapy")),
                        TextbookUnit("Unit 5", "The Human Body Systems", 180, "Musculoskeletal mechanics, human reproductive physiology, gametogenesis, and STIs epidemiology.", listOf("Sliding Filament Theory", "Reproductive Hormones", "Contraceptives & STIs")),
                        TextbookUnit("Unit 6", "Population & Natural Resources", 236, "Population ecology dynamics, carrying capacity, resource conservation, and biodiversity in Ethiopia.", listOf("Logistic Growth Models", "Renewable vs Non-renewable", "Ethiopian Biodiversity Hotspots"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Biology Student Textbook",
                    fileName = "Grade_10_Biology.pdf",
                    pageCount = 177,
                    units = listOf(
                        TextbookUnit("Unit 1", "Sub-Fields of Biology", 1, "Branches of biological sciences, scientific method, and contributions to society.", listOf("Branches of Biology", "Scientific Method", "Ethiopian Biologists")),
                        TextbookUnit("Unit 2", "Plants", 28, "Structure and function of roots, stems, leaves, flowers, pollination, and seed dispersal.", listOf("Plant Morphology", "Photosynthesis Basics", "Reproduction in Plants")),
                        TextbookUnit("Unit 3", "Biochemical Molecules", 65, "Carbohydrates, lipids, proteins, nucleic acids, and dietary requirements.", listOf("Carbohydrates & Lipids", "Proteins & Enzymes", "Nucleic Acids")),
                        TextbookUnit("Unit 4", "Cell Division", 110, "Cell cycle stages, mitosis phases, meiosis, genetic variation, and cancer biology.", listOf("Mitosis Phases", "Meiosis & Crossing Over", "Cancer & Mutation"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Biology Student Textbook",
                    fileName = "Grade_9_Biology.pdf",
                    pageCount = 210,
                    downloadUrl = OfficialBookLinks.urls["Grade_9_Biology.pdf"],
                    units = listOf(
                        TextbookUnit("Unit 1", "Introduction to Biology", 1, "Scope of biology, living things characteristics, and laboratory equipment.", listOf("Biology Meaning", "Characteristics of Life", "Microscope Usage")),
                        TextbookUnit("Unit 2", "Characteristics and Classification of Organisms", 35, "Taxonomy, binomial nomenclature, five kingdoms of classification.", listOf("Taxonomy Rules", "Five Kingdoms", "Keys for Identification")),
                        TextbookUnit("Unit 3", "Cells and Cell Theory", 78, "Cell organelles, plant vs animal cells, levels of biological organization.", listOf("Cell Organelles", "Prokaryotes vs Eukaryotes", "Tissue Levels")),
                        TextbookUnit("Unit 4", "Human Biology and Health", 125, "Nutrition, digestive system, circulatory system, and disease prevention.", listOf("Human Digestion", "Blood Circulation", "Hygiene & Diseases"))
                    )
                )
            )

            lower.contains("chem") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Chemistry Student Textbook",
                    fileName = "Grade_12_Chemistry.pdf",
                    pageCount = 298,
                    units = listOf(
                        TextbookUnit("Unit 1", "Acid-Base Equilibria", 1, "Arrhenius, Bronsted-Lowry, Lewis theories, pH calculations, buffer solutions, and salt hydrolysis.", listOf("pH & pOH Calculations", "Buffer Capacity & Action", "Acid-Base Titration Curves")),
                        TextbookUnit("Unit 2", "Electrochemistry", 78, "Galvanic cells, Nernst equation, standard electrode potentials, electrolysis, and Faraday's laws.", listOf("Standard Electrode Potentials", "Galvanic & Electrolytic Cells", "Faraday's Quantitative Laws")),
                        TextbookUnit("Unit 3", "Industrial Chemistry", 160, "Haber-Bosch process, Contact process, Solvay process, fertilizers, metallurgy in Ethiopia.", listOf("Ammonia & Nitric Acid", "Sulfuric Acid Manufacture", "Fertilizers & Sugar Industry")),
                        TextbookUnit("Unit 4", "Polymers", 235, "Addition and condensation polymerization, synthetic plastics, natural rubber, and recycling.", listOf("Addition Polymerization", "Condensation Polymers", "Biodegradable Polymers"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Chemistry Student Textbook",
                    fileName = "Grade_11_Chemistry.pdf",
                    pageCount = 342,
                    units = listOf(
                        TextbookUnit("Unit 1", "Atomic Structure & Periodic Table", 1, "Quantum mechanical model, electronic configurations, periodic trends, and spectral lines.", listOf("Quantum Numbers", "Aufbau & Hund's Rules", "Periodic Properties")),
                        TextbookUnit("Unit 2", "Chemical Bonding", 68, "Ionic, covalent, metallic bonding, Lewis structures, VSEPR theory, hybridization, intermolecular forces.", listOf("VSEPR Geometry", "Hybridization (sp, sp2, sp3)", "Hydrogen Bonding")),
                        TextbookUnit("Unit 3", "Physical States of Matter", 148, "Ideal gas law, Dalton's law, Graham's law, kinetic molecular theory, liquids and solids.", listOf("Gas Laws & Ideal Gas Equation", "Kinetic Molecular Theory", "Phase Diagrams")),
                        TextbookUnit("Unit 4", "Chemical Kinetics", 215, "Reaction rates, rate laws, order of reaction, Arrhenius equation, catalysis.", listOf("Rate Laws & Half-Life", "Activation Energy", "Catalysts & Collision Theory")),
                        TextbookUnit("Unit 5", "Chemical Equilibrium", 270, "Law of mass action, Kc and Kp calculations, Le Chatelier's principle, solubility product (Ksp).", listOf("Equilibrium Constant Calculations", "Le Chatelier's Shifts", "Common Ion Effect & Ksp"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Chemistry Student Textbook",
                    fileName = "Grade_10_Chemistry.pdf",
                    pageCount = 306,
                    units = listOf(
                        TextbookUnit("Unit 1", "Chemical Reactions and Stoichiometry", 1, "Balancing redox reactions, mole concept, limiting reactants, percent yield.", listOf("Mole Concept & Avogadro", "Limiting Reactant", "Percent Yield")),
                        TextbookUnit("Unit 2", "Solutions", 82, "Types of solutions, solubility curves, concentration units (Molarity, Molality, PPM).", listOf("Molarity & Dilutions", "Solubility Factors", "Colligative Properties")),
                        TextbookUnit("Unit 3", "Important Inorganic Compounds", 154, "Oxides, acids, bases, and salts: nomenclature, preparation, and properties.", listOf("Acidic & Basic Oxides", "Properties of Salts", "Precipitation Reactions")),
                        TextbookUnit("Unit 4", "Introduction to Organic Chemistry", 220, "Hydrocarbons: alkanes, alkenes, alkynes, isomerism, functional groups nomenclature.", listOf("IUPAC Nomenclature", "Structural Isomerism", "Functional Groups"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Chemistry Student Textbook",
                    fileName = "Grade_9_Chemistry.pdf",
                    pageCount = 183,
                    units = listOf(
                        TextbookUnit("Unit 1", "Chemistry and Its Importance", 1, "Nature of chemistry, lab safety, scientific inquiry in chemical discoveries.", listOf("Scope of Chemistry", "Lab Equipment Safety", "Measurement Units")),
                        TextbookUnit("Unit 2", "Measurements and Units", 36, "SI units, metric prefixes, dimensional analysis, significant figures in chemistry.", listOf("SI Base Units", "Significant Figures", "Density Calculations")),
                        TextbookUnit("Unit 3", "Structure of the Atom", 74, "Subatomic particles, isotopes, atomic number, relative atomic mass.", listOf("Protons, Neutrons, Electrons", "Isotopes & Atomic Mass", "Rutherford Model")),
                        TextbookUnit("Unit 4", "Periodic Classification of Elements", 120, "Historical development of periodic table, periods, groups, metals, non-metals.", listOf("Mendeleev's Table", "Modern Periodic Law", "Valence Electrons"))
                    )
                )
            )

            lower.contains("phys") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Physics Student Textbook",
                    fileName = "Grade_12_Physics.pdf",
                    pageCount = 186,
                    units = listOf(
                        TextbookUnit("Unit 1", "Thermodynamics", 1, "Heat engines, Carnot cycle, 1st and 2nd laws of thermodynamics, entropy, PV diagrams.", listOf("First Law of Thermodynamics", "Heat Engines & Carnot Efficiency", "Entropy & Reversibility")),
                        TextbookUnit("Unit 2", "Oscillations and Waves", 45, "Simple harmonic motion, wave equations, sound speed, Doppler effect, resonance.", listOf("SHM Mathematics", "Wave Interference & Superposition", "Doppler Effect")),
                        TextbookUnit("Unit 3", "Wave Optics", 92, "Huygens principle, Young's double slit interference, diffraction gratings, polarization.", listOf("Young's Double-Slit Experiment", "Diffraction Patterns", "Brewster's Law")),
                        TextbookUnit("Unit 4", "Modern Physics", 135, "Photoelectric effect, de Broglie wavelength, Bohr model, nuclear binding energy, radioactivity.", listOf("Photoelectric Equation", "Wave-Particle Duality", "Nuclear Fission & Fusion"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Physics Student Textbook",
                    fileName = "Grade_11_Physics.pdf",
                    pageCount = 338,
                    units = listOf(
                        TextbookUnit("Unit 1", "Physics and Human Society", 1, "Significance of physics, experimental measurement techniques, precision vs accuracy.", listOf("Physics Innovations", "Error Analysis", "Dimensional Consistency")),
                        TextbookUnit("Unit 2", "Vectors", 34, "Vector addition, resolution of components, dot product, cross product in 2D and 3D.", listOf("Vector Resolution", "Scalar (Dot) Product", "Vector (Cross) Product")),
                        TextbookUnit("Unit 3", "2D Motion & Projectiles", 85, "Projectile motion formulas, circular motion kinematics, relative velocity.", listOf("Projectile Trajectory Formulas", "Centripetal Acceleration", "Relative Velocity")),
                        TextbookUnit("Unit 4", "Dynamics & Newton's Laws", 142, "Newton's laws of motion, friction, impulse, linear momentum conservation, collisions.", listOf("Newton's 3 Laws Applications", "Frictional Forces", "Elastic & Inelastic Collisions")),
                        TextbookUnit("Unit 5", "Work, Energy, and Power", 205, "Work-energy theorem, conservative forces, potential energy curves, power efficiency.", listOf("Work-Kinetic Energy Theorem", "Conservation of Mechanical Energy", "Power Output")),
                        TextbookUnit("Unit 6", "Rotational Dynamics", 260, "Torque, moment of inertia, angular momentum conservation, rotational kinetic energy.", listOf("Torque Calculations", "Moment of Inertia Theorems", "Conservation of Angular Momentum"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Physics Student Textbook",
                    fileName = "Grade_10_Physics.pdf",
                    pageCount = 258,
                    units = listOf(
                        TextbookUnit("Unit 1", "Motion in One Dimension", 1, "Uniform motion, accelerated motion equations, graphs of motion, free fall.", listOf("Kinematic Equations", "v-t & s-t Graphs", "Free Fall Acceleration")),
                        TextbookUnit("Unit 2", "Forces and Newton's Laws", 52, "Inertia, force vectors, mass vs weight, equilibrium of forces.", listOf("Newton's First Law", "F = ma Calculations", "Equilibrium Conditions")),
                        TextbookUnit("Unit 3", "Circular Motion & Gravitation", 108, "Uniform circular motion, Newton's universal law of gravitation, planetary orbits.", listOf("Centripetal Force", "Universal Gravitation Formula", "Kepler's Planetary Laws")),
                        TextbookUnit("Unit 4", "Electrostatics", 164, "Coulomb's law, electric field lines, electric potential, capacitors in series and parallel.", listOf("Coulomb's Law", "Electric Field & Potential", "Capacitor Energy Storage")),
                        TextbookUnit("Unit 5", "Current Electricity", 212, "Ohm's law, resistivity, Kirchhoff's rules, electrical power and energy.", listOf("Resistors in Series & Parallel", "Kirchhoff's Junction & Loop Rules", "Electric Energy Costs"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Physics Student Textbook",
                    fileName = "Grade_9_Physics.pdf",
                    pageCount = 220,
                    downloadUrl = OfficialBookLinks.urls["Grade_9_Physics.pdf"],
                    units = listOf(
                        TextbookUnit("Unit 1", "Physics and Human Society", 1, "Role of physics in society, branches of physics, scientific inquiry.", listOf("Branches of Physics", "Relationship to Society", "Scientific Method")),
                        TextbookUnit("Unit 2", "Physical Quantities and Measurement", 30, "SI base and derived units, measurement instruments, errors and uncertainties.", listOf("SI Units", "Measurement Tools", "Error Analysis")),
                        TextbookUnit("Unit 3", "Motion in a Straight Line", 75, "Speed, velocity, acceleration, graphs of linear motion.", listOf("Distance vs Displacement", "Velocity Calculations", "Acceleration Graphs")),
                        TextbookUnit("Unit 4", "Forces and Newton's Laws", 130, "Types of forces, friction, Newton's three laws of motion.", listOf("Inertia & Force", "F = ma Applications", "Action-Reaction Pairs"))
                    )
                )
            )

            lower.contains("math") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Mathematics Student Textbook",
                    fileName = "Grade_12_Mathematics.pdf",
                    pageCount = 426,
                    units = listOf(
                        TextbookUnit("Unit 1", "Sequences and Series", 1, "Arithmetic and geometric progressions, sigma notation, convergence, financial math.", listOf("AP & GP Formulas", "Infinite Geometric Series", "Compound Interest & Annuity")),
                        TextbookUnit("Unit 2", "Introduction to Calculus (Limits & Derivatives)", 75, "Limits evaluation, continuity, derivative rules, tangent lines, optimization.", listOf("Limits of Functions", "Product, Quotient, Chain Rules", "Extrema & Optimization Problems")),
                        TextbookUnit("Unit 3", "Integrals and Applications", 162, "Indefinite and definite integrals, substitution method, area under curve, differential equations.", listOf("Fundamental Theorem of Calculus", "Integration by Substitution", "Area Between Curves")),
                        TextbookUnit("Unit 4", "Probability and Statistics", 240, "Permutations, combinations, conditional probability, Bayes' theorem, normal distribution.", listOf("Combinatorics", "Conditional Probability & Bayes", "Normal Distribution & Z-scores")),
                        TextbookUnit("Unit 5", "Matrices and Determinants", 330, "Matrix operations, determinants up to 3x3, Cramer's rule, inverse matrix, systems of equations.", listOf("Matrix Multiplication", "Determinants Evaluation", "Cramer's Rule for 3x3 Systems"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Mathematics Student Textbook",
                    fileName = "Grade_11_Mathematics.pdf",
                    pageCount = 494,
                    units = listOf(
                        TextbookUnit("Unit 1", "Relations and Functions", 1, "Domain, range, composite functions, inverse functions, composition algebra.", listOf("Function Domains & Inverses", "Composite Function Algebra", "Transformations of Graphs")),
                        TextbookUnit("Unit 2", "Rational Expressions & Rational Functions", 72, "Simplifying rational expressions, partial fractions, asymptotes, graphing rational functions.", listOf("Partial Fractions Decomposition", "Vertical & Horizontal Asymptotes", "Graphing Rational Curves")),
                        TextbookUnit("Unit 3", "Coordinate Geometry & Conic Sections", 155, "Lines, circles, parabolas, ellipses, hyperbolas equations and focus/directrix properties.", listOf("Equations of Circles", "Parabolas, Ellipses, Hyperbolas", "Eccentricity & Directrix")),
                        TextbookUnit("Unit 4", "Trigonometric Functions", 250, "Unit circle, radian measure, trig identities, compound angles, double angle formulas.", listOf("Unit Circle Values", "Double Angle & Half Angle Formulas", "Trigonometric Equations Solving")),
                        TextbookUnit("Unit 5", "Vectors in Two Dimensions", 345, "Components, scalar product, angle between vectors, vector projection.", listOf("Vector Dot Product", "Angle Between Two Vectors", "Vector Projections")),
                        TextbookUnit("Unit 6", "Statistics", 410, "Measures of central tendency, variance, standard deviation, grouped data analysis.", listOf("Mean, Median, Mode", "Variance & Standard Deviation", "Quartiles & Box Plots"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Mathematics Student Textbook",
                    fileName = "Grade_10_Mathematics.pdf",
                    pageCount = 394,
                    units = listOf(
                        TextbookUnit("Unit 1", "Relations and Functions", 1, "Definitions of relations, domain, range, function mapping.", listOf("Domain & Range", "One-to-one Functions", "Function Notation")),
                        TextbookUnit("Unit 2", "Polynomial Functions", 67, "Quadratic equations, remainder theorem, factor theorem, roots of polynomial equations.", listOf("Remainder & Factor Theorems", "Synthetic Division", "Quadratic Formula")),
                        TextbookUnit("Unit 3", "Exponential and Logarithmic Functions", 121, "Laws of exponents, logarithms properties, exponential decay and growth equations.", listOf("Laws of Exponents", "Properties of Logarithms", "Exponential Equations")),
                        TextbookUnit("Unit 4", "Trigonometric Functions", 199, "Sine, cosine, tangent ratios in right triangles, angle of elevation and depression.", listOf("SOH CAH TOA Ratios", "Angles of Elevation/Depression", "Trig Identities Basics"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Mathematics Student Textbook",
                    fileName = "Grade_9_Mathematics.pdf",
                    pageCount = 280,
                    downloadUrl = OfficialBookLinks.urls["Grade_9_Mathematics.pdf"],
                    units = listOf(
                        TextbookUnit("Unit 1", "The Number System", 1, "Real numbers, rational and irrational numbers, scientific notation.", listOf("Real Numbers", "Radicals & Surds", "Scientific Notation")),
                        TextbookUnit("Unit 2", "Equations and Inequalities", 50, "Linear equations, quadratic equations, linear inequalities solutions.", listOf("Linear Equations", "Quadratic Equations", "Inequalities on Number Line")),
                        TextbookUnit("Unit 3", "Geometry and Measurement", 110, "Triangles, quadrilaterals, congruence, similarity, Pythagorean theorem.", listOf("Congruence & Similarity", "Pythagorean Theorem", "Perimeter and Area")),
                        TextbookUnit("Unit 4", "Introduction to Trigonometry", 175, "Trigonometric ratios in right triangles, angle calculations.", listOf("Sine, Cosine, Tangent", "Right Triangle Trigonometry", "Applications")),
                        TextbookUnit("Unit 5", "Statistics and Probability", 220, "Data presentation, measures of central tendency, simple probability.", listOf("Bar Graphs & Histograms", "Mean, Median, Mode", "Basic Probability"))
                    )
                )
            )

            lower.contains("eng") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 English Student Textbook",
                    fileName = "Grade_12_English.pdf",
                    pageCount = 270,
                    units = listOf(
                        TextbookUnit("Unit 1", "Sustainable Development", 1, "Reading comprehension, environmental vocabulary, advanced passive voice.", listOf("Reading on Sustainability", "Passive Voice Mastery", "Essay Writing Structure")),
                        TextbookUnit("Unit 2", "Time Management & Goal Setting", 30, "Conditional sentences (Types 1, 2, 3, Mixed), vocabulary for scheduling and prioritization.", listOf("Conditional Clauses", "Time Management Idioms", "Formal Email Writing")),
                        TextbookUnit("Unit 3", "Evidence on Road Traffic Safety", 58, "Modal verbs of necessity and deduction, data interpretation from tables and bar charts.", listOf("Modal Verbs (Must, Should, Could)", "Analyzing Statistical Tables", "Cause & Effect Connectors")),
                        TextbookUnit("Unit 4", "Natural Resource Management", 88, "Relative clauses (defining vs non-defining), vocabulary on minerals and forestry.", listOf("Defining vs Non-defining Clauses", "Synthesizing Reading Texts", "Persuasive Argumentation")),
                        TextbookUnit("Unit 5", "Mechanization in Agriculture", 120, "Reported speech statements and questions, agricultural terminology in Ethiopian context.", listOf("Reported Speech Rules", "Tense Shifts in Indirect Speech", "Summary Writing"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 English Student Textbook",
                    fileName = "Grade_11_English.pdf",
                    pageCount = 290,
                    units = listOf(
                        TextbookUnit("Unit 1", "The Physical World", 1, "Descriptive writing, adjectives order, vocabulary on geography and climate.", listOf("Adjective Order Rules", "Descriptive Essays", "Vocabulary in Context")),
                        TextbookUnit("Unit 2", "Cultural Heritage of Ethiopia", 32, "Past simple vs past perfect, narrative structures, indigenous traditions.", listOf("Past Narrative Tenses", "Phrasal Verbs", "Oral Literature Analysis")),
                        TextbookUnit("Unit 3", "Health and Wellbeing", 64, "Gerunds vs Infinitives, health terminology, expressing advice and warnings.", listOf("Gerunds and Infinitives", "Giving Medical Advice", "Argumentative Essays")),
                        TextbookUnit("Unit 4", "Indigenous Technologies", 98, "Connectors of contrast and concession (Although, Despite, However), traditional craft systems.", listOf("Concession Connectors", "Passive in Technical Descriptions", "Vocabulary on Metallurgy & Weaving"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 English Student Textbook",
                    fileName = "Grade_10_English.pdf",
                    pageCount = 326,
                    units = listOf(
                        TextbookUnit("Unit 1", "Education & Lifelong Learning", 1, "Present perfect tense vs past simple, classroom discussion vocabulary.", listOf("Present Perfect Tense", "Collocations", "Paragraph Writing")),
                        TextbookUnit("Unit 2", "Traditional Games & Sports", 38, "Comparative and superlative forms, vocabulary on athletic games in Ethiopia.", listOf("Comparatives & Superlatives", "Sports Vocabulary", "Speech Delivery")),
                        TextbookUnit("Unit 3", "Environmental Conservation", 76, "Future tenses (Will vs Going to vs Present Continuous), ecology reading texts.", listOf("Expressing Future Plans", "Environmental Vocabulary", "Letter of Application"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 English Student Textbook",
                    fileName = "Grade_9_English.pdf",
                    pageCount = 250,
                    downloadUrl = OfficialBookLinks.urls["Grade_9_English.pdf"],
                    units = listOf(
                        TextbookUnit("Unit 1", "Living in a Community", 1, "Community life, vocabulary, present simple vs present continuous.", listOf("Community Vocabulary", "Present Tenses", "Paragraph Organization")),
                        TextbookUnit("Unit 2", "Water and Life", 35, "Water resources, reading comprehension, countable and uncountable nouns.", listOf("Environmental Reading", "Countable/Uncountable", "Expressing Quantity")),
                        TextbookUnit("Unit 3", "Health and Fitness", 70, "Healthy lifestyles, modal verbs for advice, writing informal letters.", listOf("Health Vocabulary", "Should & Must", "Letter Writing")),
                        TextbookUnit("Unit 4", "Traditional Agriculture in Ethiopia", 115, "Farming practices, past simple narrative, passive voice basics.", listOf("Agricultural Terms", "Past Tense Narratives", "Passive Structures"))
                    )
                )
            )

            lower.contains("econ") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Economics Student Textbook",
                    fileName = "Grade_12_Economics.pdf",
                    pageCount = 214,
                    units = listOf(
                        TextbookUnit("Unit 1", "Fundamental Concepts of Macroeconomics", 1, "Macroeconomic goals, circular flow model, GDP, GNP, and national income metrics.", listOf("GDP vs GNP Measurements", "Circular Flow of Income", "Nominal vs Real GDP")),
                        TextbookUnit("Unit 2", "Aggregate Demand and Aggregate Supply", 45, "AD/AS model, classical vs Keynesian perspectives, fiscal and monetary policies.", listOf("AD & AS Curves", "Multiplier Effect", "Monetary vs Fiscal Policies")),
                        TextbookUnit("Unit 3", "Market Failure and Consumer Protection", 95, "Externalities, public goods, market power, government intervention policies in Ethiopia.", listOf("Negative & Positive Externalities", "Public Goods Non-rivalry", "Price Ceilings & Floors")),
                        TextbookUnit("Unit 4", "Tax Theory and Practice in Ethiopia", 145, "Direct vs indirect taxes, principles of taxation, Ethiopian tax structure (ERCA).", listOf("Progressive vs Regressive Taxes", "VAT & Customs Duties in Ethiopia", "Tax Incidence & Deadweight Loss"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Economics Student Textbook",
                    fileName = "Grade_11_Economics.pdf",
                    pageCount = 202,
                    units = listOf(
                        TextbookUnit("Unit 1", "Theory of Consumer Behavior and Demand", 1, "Cardinal vs ordinal utility, indifference curve analysis, budget constraint line.", listOf("Marginal Utility Diminishing Law", "Indifference Curves Properties", "Consumer Equilibrium Condition")),
                        TextbookUnit("Unit 2", "Market Structure and Decision of Firms", 48, "Perfect competition, monopoly, monopolistic competition, oligopoly equilibrium.", listOf("Profit Maximization (MR=MC)", "Monopoly Deadweight Loss", "Game Theory & Cartels")),
                        TextbookUnit("Unit 3", "National Income Accounting", 98, "Expenditure, income, and output approaches, GDP deflator, cost of living index.", listOf("Expenditure Approach Components", "GDP Deflator vs CPI", "Limitations of GDP")),
                        TextbookUnit("Unit 4", "Consumption, Saving, and Investment", 152, "Keynesian consumption function, MPC, MPS, investment determinants in developing economies.", listOf("Marginal Propensity to Consume", "Investment Multiplier", "Determinants of Capital Formation"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Economics Student Textbook",
                    fileName = "Grade_10_Economics.pdf",
                    pageCount = 190,
                    units = listOf(
                        TextbookUnit("Unit 1", "Theory of Consumer Behaviour", 1, "Wants, scarcity, choice, opportunity cost, and utility satisfaction principles.", listOf("Scarcity and Choice", "Opportunity Cost Calculation", "Utility Concepts")),
                        TextbookUnit("Unit 2", "Theories of Demand and Supply", 42, "Law of demand, shifts vs movements along curve, price elasticity of demand.", listOf("Determinants of Demand", "Price Elasticity Formula", "Market Equilibrium Equilibrium")),
                        TextbookUnit("Unit 3", "Theories of Production and Cost", 90, "Short-run production functions, law of diminishing returns, fixed vs variable costs.", listOf("Total, Marginal & Average Product", "Cost Curves (ATC, AVC, MC)", "Economies of Scale"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Economics Student Textbook",
                    fileName = "Grade_9_Economics.pdf",
                    pageCount = 119,
                    units = listOf(
                        TextbookUnit("Unit 1", "Introduction to Economics", 1, "Definition of economics, micro vs macro, factors of production in economy.", listOf("Economic Resources", "Micro vs Macroeconomics", "Positive vs Normative Statements")),
                        TextbookUnit("Unit 2", "The Basic Economic Problems", 28, "What, how, and for whom to produce, economic systems (traditional, command, market, mixed).", listOf("Fundamental Economic Questions", "Command vs Market Systems", "Production Possibility Frontier")),
                        TextbookUnit("Unit 3", "Main Sectors of the Ethiopian Economy", 62, "Agriculture sector significance, industrial development, services sector contribution.", listOf("Ethiopian Agriculture Structure", "Industrialization Challenges", "Export Commodities"))
                    )
                )
            )

            lower.contains("geog") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Geography Student Textbook",
                    fileName = "Grade_12_Geography.pdf",
                    pageCount = 266,
                    units = listOf(
                        TextbookUnit("Unit 1", "Major Geological Processes of the Earth", 1, "Plate tectonics, faulting, folding, volcanism, earthquakes, and East African Rift Valley.", listOf("Plate Tectonics Theory", "East African Rift System", "Earthquake Epicenters & Waves")),
                        TextbookUnit("Unit 2", "Climate Change: Global and Local Dimensions", 60, "Greenhouse gases, global warming causes, impacts on Ethiopian agriculture and hydrology.", listOf("Greenhouse Effect Mechanics", "Mitigation vs Adaptation Strategies", "Drought Patterns in Horn of Africa")),
                        TextbookUnit("Unit 3", "Issues in Sustainable Resource Management", 125, "Water resource basins of Ethiopia, GERD, soil erosion control, wildlife parks conservation.", listOf("Major River Basins of Ethiopia", "Grand Ethiopian Renaissance Dam", "Soil Degradation Countermeasures")),
                        TextbookUnit("Unit 4", "Geographical Information Systems (GIS) and Remote Sensing", 195, "Components of GIS, spatial data models, raster vs vector, satellite imagery analysis.", listOf("GIS Spatial Data Models", "Remote Sensing Principles", "Map Projections & Scales"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Geography Student Textbook",
                    fileName = "Grade_11_Geography.pdf",
                    pageCount = 254,
                    units = listOf(
                        TextbookUnit("Unit 1", "Formation of Continents and Oceans", 1, "Continental drift theory (Wegener), seafloor spreading, paleomagnetism, structural landforms.", listOf("Continental Drift Evidence", "Seafloor Spreading", "Morphology of Ocean Floors")),
                        TextbookUnit("Unit 2", "Natural Resources and Sustainable Utilization", 55, "Forests, mineral deposits, energy resources, sustainable development paradigms.", listOf("Forest Canopy Layers", "Mineral Resources Distribution", "Sustainable Development Goals")),
                        TextbookUnit("Unit 3", "Global Population Dynamics", 118, "Demographic transition model, population pyramids, migration push/pull factors.", listOf("Demographic Transition Stages", "Age-Sex Pyramids Interpretation", "International Migration Trends")),
                        TextbookUnit("Unit 4", "Economic Geography & Trade", 185, "Primary, secondary, tertiary activities, international trade balance, globalization.", listOf("Sectors of Economic Activity", "Terms of Trade", "Economic Regional Blocs"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Geography Student Textbook",
                    fileName = "Grade_10_Geography.pdf",
                    pageCount = 214,
                    units = listOf(
                        TextbookUnit("Unit 1", "Landforms of Africa", 1, "Plateaus, mountains, rift valleys, drainage basins of the African continent.", listOf("Topography of Africa", "Major African Rivers (Nile, Congo)", "African Coastal Plains")),
                        TextbookUnit("Unit 2", "Climate of Africa", 52, "Air masses, ITCZ movement, rainfall regimes, vegetation zones in Africa.", listOf("ITCZ Seasonal Migration", "Equatorial to Desert Climates", "Vegetation Belts")),
                        TextbookUnit("Unit 3", "Natural Resource Base of Africa", 104, "Water, soils, mineral wealth of Africa, exploitation challenges.", listOf("Major Mineral Deposits", "Soil Conservation in Africa", "Hydroelectric Potential")),
                        TextbookUnit("Unit 4", "Population of Africa", 158, "Growth rates, distribution factors, urban growth, demographic challenges.", listOf("Population Growth Trends", "Urbanization in African Cities", "Dependency Ratio Issues"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Geography Student Textbook",
                    fileName = "Grade_9_Geography.pdf",
                    pageCount = 237,
                    units = listOf(
                        TextbookUnit("Unit 1", "Geological History and Topography of Ethiopia", 1, "Precambrian, Mesozoic, Cenozoic eras geological events in Horn of Africa.", listOf("Geological Eras of Ethiopia", "Mesozoic Marine Transgression", "Tertiary Volcanism & Traps")),
                        TextbookUnit("Unit 2", "Climate of Ethiopia", 64, "Altitude influence on temperature, climatic zones (Bereha, Kolla, Woina Dega, Dega, Wurch).", listOf("Ethiopian Traditional Climate Zones", "Rainfall Seasons (Kiremt, Belg, Bega)", "Lapse Rate Calculations")),
                        TextbookUnit("Unit 3", "Drainage Systems of Ethiopia", 130, "Western, South-Eastern, and Rift Valley drainage systems, lakes of Ethiopia.", listOf("Abay, Tekeze, Baro Basins", "Rift Valley Lakes", "Water Tower of East Africa"))
                    )
                )
            )

            lower.contains("hist") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 History Student Textbook",
                    fileName = "Grade_12_History.pdf",
                    pageCount = 290,
                    units = listOf(
                        TextbookUnit("Unit 1", "Ethiopian History: 1941 to 1991", 1, "Post-liberation restoration, Emperor Haile Selassie regime, the 1974 revolution, and the Derg era.", listOf("Restoration Period (1941-1974)", "1960 Coup d'Etat Attempt", "1974 Popular Revolution", "Derg Regime & Red Terror")),
                        TextbookUnit("Unit 2", "World History: The Cold War Era (1945–1991)", 75, "Origins of Cold War, NATO vs Warsaw Pact, proxy conflicts (Korea, Vietnam), decolonization of Africa.", listOf("Cold War Ideological Struggle", "Decolonization Movements in Africa", "Non-Aligned Movement")),
                        TextbookUnit("Unit 3", "Major Developments in Post-1991 World and Ethiopia", 160, "Collapse of Soviet Union, transition period in Ethiopia, adoption of 1995 FDRE Constitution.", listOf("Fall of Berlin Wall & USSR Collapse", "1991-1995 Transition in Ethiopia", "1995 FDRE Constitution Principles"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 History Student Textbook",
                    fileName = "Grade_11_History.pdf",
                    pageCount = 270,
                    units = listOf(
                        TextbookUnit("Unit 1", "History, Historiography, and Human Evolution", 1, "Sources of history, historical methodology, hominid evolution evidence from Ethiopian sites.", listOf("Primary vs Secondary Sources", "Afar & Omo Paleolithic Discoveries", "Prehistoric Stone Tool Ages")),
                        TextbookUnit("Unit 2", "Ancient Civilizations up to c. 500 AD", 52, "Mesopotamia, Egypt, Indus Valley, ancient Greece, Rome, Da'amat and Aksumite kingdom.", listOf("Aksumite Inscriptions & Coins", "Aksum's Red Sea Trade", "Introduction of Christianity & Islam")),
                        TextbookUnit("Unit 3", "Ethiopia and the Horn from Medieval to Modern (1270–1855)", 120, "Restoration of Solomonic dynasty, Christian-Muslim sultanates conflict, Ahmad Gragn wars, Gadaa system expansion.", listOf("Solomonic Dynastic Revival", "Wars of Ahmad Ibrahim (Gragn)", "Oromo Population Movements & Gadaa", "Zemene Mesafint (Era of Princes)"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 History Student Textbook",
                    fileName = "Grade_10_History.pdf",
                    pageCount = 246,
                    units = listOf(
                        TextbookUnit("Unit 1", "State Formation and Modernization in Ethiopia (1855–1913)", 1, "Tewodros II centralisation, Yohannes IV defense against foreign aggression, Menelik II expansion and Battle of Adwa (1896).", listOf("Tewodros II State-building", "Yohannes IV & Battle of Metemma", "Battle of Adwa 1896 Victory", "Creation of Modern Ethiopian Borders")),
                        TextbookUnit("Unit 2", "First World War and Its Aftermath (1914–1939)", 85, "Causes of WWI, Treaty of Versailles, rise of fascism and Nazism, League of Nations failure.", listOf("Causes of World War I", "Treaty of Versailles Impacts", "Rise of Mussolini and Hitler")),
                        TextbookUnit("Unit 3", "The Italian Fascist Aggression on Ethiopia (1935–1941)", 165, "Walwal incident, Italian invasion, patriotic resistance movement (Arbegnoch), British intervention and liberation.", listOf("Walwal Incident 1934", "Patriotic Resistance Movement", "1941 Liberation of Addis Ababa"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 History Student Textbook",
                    fileName = "Grade_9_History.pdf",
                    pageCount = 219,
                    units = listOf(
                        TextbookUnit("Unit 1", "Introduction to the Study of History", 1, "Meaning of history, chronology, eras, dating systems, significance of studying Ethiopian heritage.", listOf("Definition & Nature of History", "Dating Systems (BC/AD, BCE/CE)", "Value of Historical Heritage")),
                        TextbookUnit("Unit 2", "Human Evolution and the Emergence of Agriculture", 48, "Australopithecus afarensis, Homo erectus, Neolithic agricultural revolution in Africa.", listOf("Ethiopia Cradle of Humankind", "Neolithic Agricultural Revolution", "Domestication of Crops in Horn of Africa")),
                        TextbookUnit("Unit 3", "Ancient Civilizations of Africa", 110, "Kush/Meroe, Carthage, Axumite civilization, early trade networks across Sahara and Red Sea.", listOf("Kingdom of Kush and Meroe", "Ancient Red Sea Maritime Trade", "Early Ethiopian Stone Monuments"))
                    )
                )
            )

            else -> listOf(
                TextbookEdition(
                    grade = "General",
                    title = "$subjectName Official Curriculum",
                    fileName = "General_Curriculum.pdf",
                    pageCount = 200,
                    units = listOf(
                        TextbookUnit("Unit 1", "Core Foundations", 1, "Foundational principles and learning objectives according to Ministry of Education.", listOf("Core Objectives", "Essential Competencies", "Assessment Framework")),
                        TextbookUnit("Unit 2", "Advanced Applications", 60, "Higher-order problem solving and examination preparation strategies.", listOf("Case Studies", "Exam Questions Analysis", "Summary Review"))
                    )
                )
            )
        }
    }

    fun getAllTextbooksForGrade(grade: String): List<TextbookEdition> {
        val subjects = listOf("Biology", "Chemistry", "Physics", "Mathematics", "English", "Economics", "Geography", "History")
        val allEditions = mutableListOf<TextbookEdition>()
        for (subj in subjects) {
            val ed = getTextbooksForSubject(subj).firstOrNull { it.grade == grade }
            if (ed != null) allEditions.add(ed)
        }
        return allEditions
    }
}

/**
 * Memory-bounded lazy PDF page cache.
 * Keeps at most [maxEntries] rendered page bitmaps in memory (LRU eviction),
 * lazily rendering pages on demand on an IO/Default dispatcher to avoid OOM.
 */
object PdfPageBitmapCache {
    private val memoryCache = object : android.util.LruCache<String, Bitmap>(6) {
        override fun entryRemoved(evicted: Boolean, key: String?, oldValue: Bitmap?, newValue: Bitmap?) {
            // Let GC reclaim recycled bitmaps safely
            if (evicted && oldValue != null && !oldValue.isRecycled) {
                // Don't manually recycle if it might still be referenced by an active Compose frame,
                // rely on Android GC after removal from LRU cache.
            }
        }
    }

    fun get(key: String): Bitmap? = synchronized(this) {
        val bmp = memoryCache.get(key)
        if (bmp != null && !bmp.isRecycled) bmp else null
    }

    fun put(key: String, bitmap: Bitmap) = synchronized(this) {
        memoryCache.put(key, bitmap)
    }

    fun clear() = synchronized(this) {
        memoryCache.evictAll()
    }
}

suspend fun renderPdfPageBitmap(
    pdfFile: File,
    pageNumber: Int, // 1-indexed
    scaleFactor: Float = 2.0f
): Bitmap? = kotlinx.coroutines.withContext(Dispatchers.IO) {
    val cacheKey = "${pdfFile.absolutePath}_p${pageNumber}_s$scaleFactor"
    PdfPageBitmapCache.get(cacheKey)?.let { return@withContext it }

    try {
        ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY).use { pfd ->
            PdfRenderer(pfd).use { renderer ->
                val safePageIdx = (pageNumber - 1).coerceIn(0, renderer.pageCount - 1)
                renderer.openPage(safePageIdx).use { page ->
                    val renderWidth = (page.width * scaleFactor).toInt().coerceAtLeast(800)
                    val renderHeight = (page.height * scaleFactor).toInt().coerceAtLeast(1100)
                    val bmp = Bitmap.createBitmap(renderWidth, renderHeight, Bitmap.Config.ARGB_8888)
                    bmp.eraseColor(android.graphics.Color.WHITE)
                    val canvas = Canvas(bmp)
                    canvas.drawColor(android.graphics.Color.WHITE)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    PdfPageBitmapCache.put(cacheKey, bmp)
                    bmp
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

suspend fun downloadTextbookToCache(
    url: String,
    dest: File,
    onProgress: (Int) -> Unit
): Boolean = kotlinx.coroutines.withContext(Dispatchers.IO) {
    val tempFile = File(dest.parentFile, "${dest.name}.${System.currentTimeMillis()}.downloading")
    try {
        var currentUrl = url
        var conn: java.net.HttpURLConnection? = null
        var redirectCount = 0
        while (redirectCount < 5) {
            val u = java.net.URL(currentUrl)
            conn = (u.openConnection() as java.net.HttpURLConnection).apply {
                instanceFollowRedirects = true
                connectTimeout = 15000
                readTimeout = 20000
                setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                connect()
            }
            val status = conn.responseCode
            if (status in listOf(301, 302, 303, 307, 308)) {
                val newLoc = conn.getHeaderField("Location")
                if (!newLoc.isNullOrBlank()) {
                    currentUrl = if (newLoc.startsWith("http://") || newLoc.startsWith("https://")) {
                        newLoc
                    } else {
                        java.net.URL(u, newLoc).toString()
                    }
                    conn.disconnect()
                    redirectCount++
                    continue
                }
            }
            break
        }

        if (conn == null || conn.responseCode !in 200..299) {
            conn?.disconnect()
            return@withContext false
        }

        val totalLen = conn.contentLengthLong
        conn.inputStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                val buf = ByteArray(8192)
                var r: Int
                var total = 0L
                while (input.read(buf).also { r = it } != -1) {
                    output.write(buf, 0, r)
                    total += r
                    if (totalLen > 0) {
                        onProgress(((total * 100) / totalLen).toInt().coerceIn(0, 99))
                    }
                }
                output.flush()
            }
        }
        conn.disconnect()

        // PDF Signature Validation
        var isValidPdf = false
        if (tempFile.exists() && tempFile.length() > 5) {
            try {
                FileInputStream(tempFile).use { fis ->
                    val header = ByteArray(5)
                    if (fis.read(header) == 5) {
                        // %PDF-
                        if (header[0] == 0x25.toByte() && header[1] == 0x50.toByte() &&
                            header[2] == 0x44.toByte() && header[3] == 0x46.toByte() &&
                            header[4] == 0x2D.toByte()) {
                            isValidPdf = true
                        }
                    }
                }
            } catch (e: Exception) {
                isValidPdf = false
            }
        }

        if (isValidPdf && tempFile.length() > 0) {
            if (dest.exists()) dest.delete()
            val renamed = tempFile.renameTo(dest)
            if (!renamed) {
                // Fallback to copy and delete if rename fails across file system boundaries
                tempFile.copyTo(dest, overwrite = true)
                tempFile.delete()
            }
            onProgress(100)
            true
        } else {
            if (tempFile.exists()) tempFile.delete()
            false
        }
    } catch (_: Exception) {
        if (tempFile.exists()) tempFile.delete()
        false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficialTextbookScreen(
    subjectName: String,
    onClose: () -> Unit,
    viewModel: StudyViewModel? = null
) {
    val editions = remember(subjectName) { OfficialTextbookRegistry.getTextbooksForSubject(subjectName) }
    var selectedGrade by remember { mutableStateOf(viewModel?.selectedGradeFilter?.value ?: editions.firstOrNull()?.grade ?: "Grade 12") }
    var selectedUnitIndex by remember { mutableStateOf<Int?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var activeReaderUnitPage by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    val currentEdition = editions.firstOrNull { it.grade == selectedGrade } ?: editions.firstOrNull()

    var reloadTrigger by remember { mutableStateOf(0) }
    val isCurrentBookCached = remember(currentEdition, reloadTrigger) {
        if (currentEdition == null) false
        else {
            val f = getOrCreateTextbookPdfFile(context, currentEdition)
            f != null && f.exists() && f.length() > 0
        }
    }
    var showDownloadPromptModal by remember { mutableStateOf(!isCurrentBookCached) }
    var modalDownloadProgress by remember { mutableStateOf<Int?>(null) }
    var modalDownloadError by remember { mutableStateOf<String?>(null) }
    var showDownloadAllPromptModal by remember { mutableStateOf(false) }
    var downloadAllOverallProgress by remember { mutableStateOf<Float?>(null) }
    var downloadAllStatusText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedGrade) {
        val f = if (currentEdition != null) getOrCreateTextbookPdfFile(context, currentEdition) else null
        val cached = f != null && f.exists() && f.length() > 0
        if (!cached) {
            showDownloadPromptModal = true
        }
    }

    val startDownload: (TextbookEdition) -> Unit = { edition ->
        val bookDownloadUrl = edition.downloadUrl ?: OfficialBookLinks.urls[edition.fileName]
        if (bookDownloadUrl != null) {
            scope.launch {
                modalDownloadProgress = 0
                modalDownloadError = null
                val dir = File(context.filesDir, "official_textbooks")
                if (!dir.exists()) dir.mkdirs()
                val dest = File(dir, edition.fileName)
                val ok = downloadTextbookToCache(bookDownloadUrl, dest) { modalDownloadProgress = it }
                if (ok) {
                    kotlinx.coroutines.delay(300)
                    modalDownloadProgress = null
                    reloadTrigger++
                    showDownloadPromptModal = false
                } else {
                    modalDownloadError = "Unable to download. Please check your network connection."
                    modalDownloadProgress = null
                }
            }
        } else {
            modalDownloadError = "Curriculum textbook will be available in the next sync."
        }
    }

    val startDownloadAll: () -> Unit = {
        scope.launch {
            // "Download All" specifically downloads the textbooks for the current subject (e.g. English, Mathematics, etc.)
            val subjectBooks = editions

            val toDownload = subjectBooks.filter { 
                val f = getOrCreateTextbookPdfFile(context, it)
                f == null || !f.exists() || f.length() == 0L
            }
            if (toDownload.isEmpty()) {
                downloadAllStatusText = "All $subjectName textbooks are already downloaded!"
                kotlinx.coroutines.delay(2000)
                showDownloadAllPromptModal = false
                return@launch
            }
            
            var completedCount = 0
            val totalCount = toDownload.size
            val dir = File(context.filesDir, "official_textbooks")
            if (!dir.exists()) dir.mkdirs()
            
            for (book in toDownload) {
                val cleanTitle = "${book.grade} ${book.title.replace("Student Textbook", "").trim()}"
                downloadAllStatusText = "Downloading $cleanTitle..."
                val bookDownloadUrl = book.downloadUrl ?: OfficialBookLinks.urls[book.fileName]
                if (bookDownloadUrl != null) {
                    val dest = File(dir, book.fileName)
                    downloadTextbookToCache(bookDownloadUrl, dest) { currentBookProgress ->
                        downloadAllOverallProgress = (completedCount.toFloat() + (currentBookProgress / 100f)) / totalCount
                    }
                }
                completedCount++
                downloadAllOverallProgress = completedCount.toFloat() / totalCount
                reloadTrigger++
            }
            downloadAllStatusText = "All $subjectName downloads complete!"
            kotlinx.coroutines.delay(1500)
            showDownloadAllPromptModal = false
            downloadAllOverallProgress = null
        }
    }

    // Modal popup to prompt downloading official textbook with visible percentages
    if (showDownloadPromptModal && currentEdition != null && !isCurrentBookCached) {
        AlertDialog(
            onDismissRequest = {
                if (modalDownloadProgress == null) {
                    showDownloadPromptModal = false
                }
            },
            containerColor = Color(0xFF131C2E),
            icon = {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(EmeraldPrimary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            title = {
                Text(
                    text = "Download Official Textbook",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${currentEdition.grade} • ${currentEdition.title}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = HolographicAqua,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Official Ministry of Education curriculum textbook (${currentEdition.pageCount} pages). Download to your device for high-speed offline access with complete diagrams, exercises, and chapters.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center
                    )

                    if (modalDownloadProgress != null) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(100.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { 1f },
                                modifier = Modifier.fillMaxSize(),
                                color = Color(0xFF334155),
                                strokeWidth = 8.dp
                            )
                            CircularProgressIndicator(
                                progress = { (modalDownloadProgress ?: 0) / 100f },
                                modifier = Modifier.fillMaxSize(),
                                color = EmeraldPrimary,
                                strokeWidth = 8.dp,
                                strokeCap = StrokeCap.Round
                            )
                            Text(
                                text = "${modalDownloadProgress ?: 0}%",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = EmeraldPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Downloading Official Textbook...",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.LightGray
                        )
                    }

                    if (modalDownloadError != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = modalDownloadError!!,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFEF4444),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            confirmButton = {
                if (modalDownloadProgress != null) {
                    Button(
                        onClick = {},
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = EmeraldPrimary.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Downloading (${modalDownloadProgress}%)...", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = { startDownload(currentEdition) },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Download Official PDF (${currentEdition.pageCount}p)", fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                if (modalDownloadProgress == null) {
                    TextButton(onClick = { showDownloadPromptModal = false }) {
                        Text("Later / Browse Units", color = TextMuted)
                    }
                }
            }
        )
    }

    if (showDownloadAllPromptModal) {
        AlertDialog(
            onDismissRequest = {
                if (downloadAllOverallProgress == null) showDownloadAllPromptModal = false
            },
            containerColor = Color(0xFF131C2E),
            icon = {
                Box(
                    modifier = Modifier.size(52.dp).clip(CircleShape).background(EmeraldPrimary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CloudDownload, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(28.dp))
                }
            },
            title = {
                Text(
                    text = "Download $subjectName Textbooks",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Download all official textbooks for $subjectName across grades for offline study.",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (downloadAllOverallProgress != null) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                            CircularProgressIndicator(progress = { 1f }, modifier = Modifier.fillMaxSize(), color = Color(0xFF334155), strokeWidth = 8.dp)
                            CircularProgressIndicator(
                                progress = { downloadAllOverallProgress ?: 0f },
                                modifier = Modifier.fillMaxSize(),
                                color = EmeraldPrimary,
                                strokeWidth = 8.dp,
                                strokeCap = StrokeCap.Round
                            )
                            Text(
                                text = "${((downloadAllOverallProgress ?: 0f) * 100).toInt()}%",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = EmeraldPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = downloadAllStatusText,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.LightGray
                        )
                    } else if (downloadAllStatusText.isNotEmpty()) {
                        Text(text = downloadAllStatusText, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            },
            confirmButton = {
                if (downloadAllOverallProgress != null) {
                    Button(
                        onClick = {},
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = EmeraldPrimary.copy(alpha = 0.5f))
                    ) {
                        Text("Downloading...", color = Color.White)
                    }
                } else if (downloadAllStatusText.isEmpty()) {
                    Button(
                        onClick = { startDownloadAll() },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Text("Start Download", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                if (downloadAllOverallProgress == null) {
                    TextButton(onClick = { showDownloadAllPromptModal = false }) {
                        Text("Cancel", color = TextMuted)
                    }
                }
            }
        )
    }

    // If in-app reader is opened, render it directly full-screen
    if (activeReaderUnitPage != null && currentEdition != null) {
        InAppPdfTextbookReader(
            edition = currentEdition,
            initialPage = activeReaderUnitPage!!,
            onClose = { activeReaderUnitPage = null },
            viewModel = viewModel
        )
        return
    }

    val filteredUnits = remember(currentEdition, searchQuery) {
        val allUnits = currentEdition?.units ?: emptyList()
        if (searchQuery.isBlank()) allUnits
        else allUnits.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.unitNumber.contains(searchQuery, ignoreCase = true) ||
            it.summary.contains(searchQuery, ignoreCase = true) ||
            it.keyTopics.any { topic -> topic.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // 1. Premium Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(EmeraldPrimary.copy(alpha = 0.15f))
                        .border(1.dp, EmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "OFFICIAL TEXTBOOK",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.2.sp
                            ),
                            color = GoldAccent
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF1E293B)
                        ) {
                            Text(
                                text = "FDRE MoE",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = subjectName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Download All button on top bar
                TextButton(
                    onClick = {
                        if (downloadAllOverallProgress == null) {
                            showDownloadAllPromptModal = true
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E293B)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    if (downloadAllOverallProgress != null) {
                        CircularProgressIndicator(
                            progress = { downloadAllOverallProgress ?: 0f },
                            modifier = Modifier.size(16.dp),
                            color = EmeraldPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "${((downloadAllOverallProgress ?: 0f) * 100).toInt()}%",
                            color = EmeraldPrimary,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.CloudDownload,
                            contentDescription = "Download All",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Download All",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        val activity = AdsManager.findActivity(context)
                        if (activity != null) {
                            AdsManager.showInterstitialIfAllowed(activity) {
                                onClose()
                            }
                        } else {
                            onClose()
                        }
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
        }

        // 2. Grade Level Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A).copy(alpha = 0.8f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            editions.forEach { edition ->
                val isSelected = edition.grade == selectedGrade
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            selectedGrade = edition.grade
                            selectedUnitIndex = null
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) EmeraldPrimary else Color(0xFF1E293B),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) EmeraldPrimary else Color.White.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = edition.grade,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold
                            ),
                            color = if (isSelected) Color.White else Color.LightGray
                        )
                        Text(
                            text = "${edition.pageCount}p",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = if (isSelected) Color.White.copy(alpha = 0.85f) else TextMuted
                        )
                    }
                }
            }
        }

        // 3. Search Bar for Units and Topics
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search unit, chapter, or key topic...", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp)) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF131C2E),
                unfocusedContainerColor = Color(0xFF131C2E),
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = Color(0xFF334155),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )

        // 4. Main Content: Book Overview & Unit Breakdown
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Book Information Card
            if (currentEdition != null) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.3f), EmeraldPrimary.copy(alpha = 0.4f))), RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131C2E))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = currentEdition.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${currentEdition.publisher} • ${currentEdition.curriculumVersion}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldPrimary.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, EmeraldPrimary)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("MoE Verified", color = Color.White, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Action: Open in In-App PDF Reader
                            Button(
                                onClick = {
                                    if (!isCurrentBookCached) {
                                        showDownloadPromptModal = true
                                    } else {
                                        val activity = AdsManager.findActivity(context)
                                        if (activity != null) {
                                            AdsManager.showInterstitialIfAllowed(activity) {
                                                activeReaderUnitPage = 1
                                            }
                                        } else {
                                            activeReaderUnitPage = 1
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .pressBounce(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                            ) {
                                Icon(
                                    imageVector = if (isCurrentBookCached) Icons.Default.PictureAsPdf else Icons.Default.Download,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isCurrentBookCached) "Read Official ${currentEdition.grade} PDF In-App (${currentEdition.pageCount} Pages)"
                                           else "Download & Read Official ${currentEdition.grade} PDF (${currentEdition.pageCount}p)",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Section Header: Chapters & Units Breakdown
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "OFFICIAL CHAPTERS & UNITS",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = HolographicAqua
                    )
                    Text(
                        text = "${filteredUnits.size} Units",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextMuted
                    )
                }
            }

            // List of Official Units
            items(filteredUnits) { unit ->
                val isExpanded = selectedUnitIndex == filteredUnits.indexOf(unit)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            selectedUnitIndex = if (isExpanded) null else filteredUnits.indexOf(unit)
                        }
                        .border(
                            1.dp,
                            if (isExpanded) EmeraldPrimary else Color(0xFF334155),
                            RoundedCornerShape(14.dp)
                        ),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isExpanded) Color(0xFF1E293B) else Color(0xFF131C2E)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldPrimary.copy(alpha = 0.2f),
                                    modifier = Modifier.size(36.dp),
                                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f))
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = unit.unitNumber.filter { it.isDigit() }.ifEmpty { "1" },
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                            color = EmeraldPrimary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = unit.unitNumber.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                                        color = GoldAccent
                                    )
                                    Text(
                                        text = unit.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF0F172A)
                                ) {
                                    Text(
                                        text = "Page ${unit.pageStart}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = TextMuted,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = Color.LightGray
                                )
                            }
                        }

                        // Expanded unit details
                        AnimatedVisibility(visible = isExpanded) {
                            Column(modifier = Modifier.padding(top = 14.dp)) {
                                HorizontalDivider(color = Color(0xFF334155).copy(alpha = 0.6f))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "CHAPTER SUMMARY",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = HolographicAqua
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = unit.summary,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 20.sp),
                                    color = Color(0xFFCBD5E1)
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "CORE EUEE EXAMINATION TOPICS",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = GoldAccent
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                unit.keyTopics.forEach { topic ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = EmeraldPrimary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = topic,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                            color = Color.White
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            if (!isCurrentBookCached) {
                                                showDownloadPromptModal = true
                                            } else {
                                                val activity = AdsManager.findActivity(context)
                                                if (activity != null) {
                                                    AdsManager.showInterstitialIfAllowed(activity) {
                                                        activeReaderUnitPage = unit.pageStart
                                                    }
                                                } else {
                                                    activeReaderUnitPage = unit.pageStart
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .pressBounce(),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                                    ) {
                                        Icon(
                                            imageVector = if (isCurrentBookCached) Icons.Default.MenuBook else Icons.Default.Download,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isCurrentBookCached) "Read In-App PDF" else "Download & Read",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            if (viewModel != null) {
                                                viewModel.selectedGradeFilter.value = currentEdition?.grade ?: "Grade 12"
                                                viewModel.startNotes()
                                            }
                                            onClose()
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .pressBounce(),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                        border = BorderStroke(1.dp, Color(0xFF475569))
                                    ) {
                                        Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Syllabus Notes", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getOrCreateTextbookPdfFile(context: android.content.Context, edition: TextbookEdition): File? {
    val dir = File(context.filesDir, "official_textbooks")
    if (!dir.exists()) dir.mkdirs()
    val textbookFile = File(dir, edition.fileName)
    return if (textbookFile.exists() && textbookFile.length() > 0) textbookFile else null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppPdfTextbookReader(
    edition: TextbookEdition,
    initialPage: Int,
    onClose: () -> Unit,
    viewModel: StudyViewModel? = null
) {
    var currentPage by remember { mutableStateOf(initialPage.coerceIn(1, edition.pageCount)) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var showJumpDialog by remember { mutableStateOf(false) }
    var showTocModal by remember { mutableStateOf(false) }
    var selectedTocTab by remember { mutableStateOf("units") } // "units", "bookmarks"
    var fallbackBookmarks by remember { mutableStateOf(setOf<Int>()) }
    var downloadProgress by remember { mutableStateOf<Int?>(null) }
    var downloadError by remember { mutableStateOf<String?>(null) }
    var reloadTrigger by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val totalPages = edition.pageCount

    // Reactively observe saved textbook bookmarks so bookmark state updates instantly in UI
    val bookmarkedPagesSet = viewModel?.savedTextbookBookmarksSet?.collectAsState()?.value ?: emptySet()
    val isBookmarked = if (viewModel != null) {
        bookmarkedPagesSet.contains("${edition.fileName}_$currentPage")
    } else {
        fallbackBookmarks.contains(currentPage)
    }
    val editionBookmarks = if (viewModel != null) {
        val prefix = "${edition.fileName}_"
        bookmarkedPagesSet
            .filter { it.startsWith(prefix) }
            .mapNotNull { it.removePrefix(prefix).toIntOrNull() }
            .sorted()
    } else {
        fallbackBookmarks.toList().sorted()
    }

    val currentUnit = remember(currentPage, edition) {
        edition.units.findLast { it.pageStart <= currentPage } ?: edition.units.firstOrNull()
    }

    // Lazily load and render PDF pages on IO dispatcher using LRU memory cache
    var pdfBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isRenderingPage by remember { mutableStateOf(false) }

    LaunchedEffect(currentPage, edition.fileName, reloadTrigger) {
        val realPdfFile = getOrCreateTextbookPdfFile(context, edition)
        if (realPdfFile != null && realPdfFile.exists() && realPdfFile.length() > 0) {
            val cached = PdfPageBitmapCache.get("${realPdfFile.absolutePath}_p${currentPage}_s2.0")
            if (cached != null) {
                pdfBitmap = cached
            } else {
                isRenderingPage = true
                val bmp = renderPdfPageBitmap(realPdfFile, currentPage, 2.0f)
                pdfBitmap = bmp
                isRenderingPage = false
            }
        } else {
            pdfBitmap = null
        }
    }

    // Seamless auto-download in background if textbook not yet cached
    LaunchedEffect(edition.fileName) {
        val realPdfFile = getOrCreateTextbookPdfFile(context, edition)
        if ((realPdfFile == null || !realPdfFile.exists() || realPdfFile.length() == 0L) && downloadProgress == null) {
            val bookDownloadUrl = edition.downloadUrl ?: OfficialBookLinks.urls[edition.fileName]
            if (bookDownloadUrl != null) {
                val dir = File(context.filesDir, "official_textbooks")
                if (!dir.exists()) dir.mkdirs()
                val dest = File(dir, edition.fileName)
                if (!dest.exists() || dest.length() == 0L) {
                    downloadProgress = 0
                    downloadError = null
                    val ok = downloadTextbookToCache(bookDownloadUrl, dest) { downloadProgress = it }
                    if (ok) reloadTrigger++
                    else downloadError = "Offline file not ready yet. Check your connection."
                    downloadProgress = null
                }
            }
        }
    }

    // Reader UI Theme Tokens (Adapts to app light/dark theme while document retains original white-paper appearance)
    val isDarkTheme = viewModel?.isDarkTheme?.collectAsState()?.value ?: androidx.compose.foundation.isSystemInDarkTheme()
    val viewerBg = if (isDarkTheme) Color(0xFF1E1F22) else Color(0xFFF1F5F9)
    val barBg = if (isDarkTheme) Color(0xFF2B2D30) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color(0xFFE6EDF3) else Color(0xFF0F172A)
    val textMuted = if (isDarkTheme) Color(0xFF9DA5B4) else Color(0xFF64748B)
    val barBorderColor = if (isDarkTheme) Color(0xFF3C3F41) else Color(0xFFE2E8F0)

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.8f, 3.5f)
        offset += offsetChange
    }

    Scaffold(
        containerColor = viewerBg,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        val activity = AdsManager.findActivity(context)
                        if (activity != null) {
                            AdsManager.showInterstitialIfAllowed(activity) {
                                onClose()
                            }
                        } else {
                            onClose()
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textColor)
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = null,
                            tint = Color(0xFFEA4335),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = edition.fileName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = textColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Page $currentPage of $totalPages • ${currentUnit?.unitNumber ?: "Chapter"}",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldPrimary
                            )
                        }
                    }
                },
                actions = {

                    // Zoom Out
                    IconButton(onClick = { scale = (scale - 0.25f).coerceAtLeast(0.8f) }) {
                        Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out", tint = textColor)
                    }
                    // Zoom In
                    IconButton(onClick = { scale = (scale + 0.25f).coerceAtMost(3.5f) }) {
                        Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In", tint = textColor)
                    }
                    // Jump to page
                    IconButton(onClick = { showJumpDialog = true }) {
                        Icon(Icons.Default.FindInPage, contentDescription = "Jump to Page", tint = textColor)
                    }
                    // Table of Contents & Bookmarks
                    IconButton(onClick = { showTocModal = true }) {
                        Icon(Icons.Default.FormatListBulleted, contentDescription = "Table of Contents", tint = textColor)
                    }
                    // Companion Syllabus Notes
                    if (viewModel != null) {
                        IconButton(onClick = { viewModel.startNotes() }) {
                            Icon(Icons.Default.Description, contentDescription = "Study Notes", tint = EmeraldPrimary)
                        }
                    }
                    // Bookmark Page
                    IconButton(onClick = {
                        if (viewModel != null) {
                            viewModel.toggleSaveTextbookBookmark(edition.fileName, currentPage)
                        } else {
                            fallbackBookmarks = if (isBookmarked) {
                                fallbackBookmarks - currentPage
                            } else {
                                fallbackBookmarks + currentPage
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) GoldAccent else textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = barBg)
            )
        },
        bottomBar = {
            Surface(
                color = barBg,
                border = BorderStroke(1.dp, barBorderColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .navigationBarsPadding()
                ) {
                    // Page Scrub Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("1", style = MaterialTheme.typography.labelSmall, color = textMuted)
                        Slider(
                            value = currentPage.toFloat(),
                            onValueChange = { currentPage = it.toInt().coerceIn(1, totalPages) },
                            valueRange = 1f..totalPages.toFloat(),
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = EmeraldPrimary,
                                activeTrackColor = EmeraldPrimary,
                                inactiveTrackColor = if (isDarkTheme) Color(0xFF4E5157) else Color(0xFFCBD5E1)
                            )
                        )
                        Text("$totalPages", style = MaterialTheme.typography.labelSmall, color = textMuted)
                    }

                    // Bottom Navigation Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { if (currentPage > 1) currentPage -= 1 },
                            enabled = currentPage > 1,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Prev", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isDarkTheme) Color(0xFF1E1F22) else Color(0xFFF8FAFC),
                            border = BorderStroke(1.dp, barBorderColor),
                            modifier = Modifier.clickable { showJumpDialog = true }
                        ) {
                            Text(
                                text = "Page $currentPage / $totalPages",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                                color = textColor,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Button(
                            onClick = { if (currentPage < totalPages) currentPage += 1 },
                            enabled = currentPage < totalPages,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                        ) {
                            Text("Next", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next", modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(viewerBg)
                .transformable(state = transformState),
            contentAlignment = Alignment.Center
        ) {
            if (pdfBitmap != null) {
                // Render Genuine PDF Page Bitmap rendered directly from Android PdfRenderer
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = pdfBitmap!!.asImageBitmap(),
                        contentDescription = "PDF Page $currentPage",
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(2.dp))
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color.White)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offset.x,
                                translationY = offset.y
                            )
                    )
                }
            } else if (isRenderingPage) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(color = EmeraldPrimary, strokeWidth = 3.dp)
                        Text(
                            text = "Loading Page $currentPage...",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = textColor
                        )
                    }
                }
            } else {
                val bookDownloadUrl = edition.downloadUrl ?: OfficialBookLinks.urls[edition.fileName]
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(24.dp)
                    ) {
                        if (downloadProgress != null) {
                            CircularProgressIndicator(color = EmeraldPrimary, strokeWidth = 3.dp)
                            Text(
                                text = "Downloading Official Textbook ($downloadProgress%)...",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = textColor
                            )
                            LinearProgressIndicator(
                                progress = { (downloadProgress ?: 0) / 100f },
                                modifier = Modifier.fillMaxWidth(0.75f),
                                color = EmeraldPrimary
                            )
                        } else {
                            Icon(
                                Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = edition.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = textColor,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Ministry of Education Official PDF",
                                style = MaterialTheme.typography.bodySmall,
                                color = textMuted
                            )
                            if (downloadError != null) {
                                Text(
                                    text = downloadError!!,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFEF4444),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            if (bookDownloadUrl != null) {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            downloadProgress = 0
                                            downloadError = null
                                            val dir = File(context.filesDir, "official_textbooks")
                                            if (!dir.exists()) dir.mkdirs()
                                            val dest = File(dir, edition.fileName)
                                            val ok = downloadTextbookToCache(bookDownloadUrl, dest) { downloadProgress = it }
                                            if (ok) reloadTrigger++
                                            else downloadError = "Download failed. Please check your connection."
                                            downloadProgress = null
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                                ) {
                                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Download Official PDF", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Jump to Page Dialog
    if (showJumpDialog) {
        var inputPage by remember { mutableStateOf(currentPage.toString()) }
        AlertDialog(
            onDismissRequest = { showJumpDialog = false },
            containerColor = barBg,
            title = {
                Text("Jump to Page", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = textColor)
            },
            text = {
                Column {
                    Text("Enter page number (1 to $totalPages):", style = MaterialTheme.typography.bodySmall, color = textMuted)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = inputPage,
                        onValueChange = { inputPage = it.filter { ch -> ch.isDigit() } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = barBorderColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val pageNum = inputPage.toIntOrNull()
                        if (pageNum != null && pageNum in 1..totalPages) {
                            currentPage = pageNum
                        }
                        showJumpDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Go", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showJumpDialog = false }) {
                    Text("Cancel", color = textMuted)
                }
            }
        )
    }

    // Table of Contents and Bookmarks Modal
    if (showTocModal) {
        AlertDialog(
            onDismissRequest = { showTocModal = false },
            containerColor = barBg,
            title = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Document Outline", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = textColor)
                        IconButton(onClick = { showTocModal = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = textMuted)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedTocTab = "units" },
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedTocTab == "units") EmeraldPrimary else viewerBg,
                            border = BorderStroke(1.dp, if (selectedTocTab == "units") EmeraldPrimary else barBorderColor)
                        ) {
                            Text(
                                text = "Units (${edition.units.size})",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (selectedTocTab == "units") Color.White else textColor,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedTocTab = "bookmarks" },
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedTocTab == "bookmarks") GoldAccent else viewerBg,
                            border = BorderStroke(1.dp, if (selectedTocTab == "bookmarks") GoldAccent else barBorderColor)
                        ) {
                            Text(
                                text = "Bookmarks (${editionBookmarks.size})",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (selectedTocTab == "bookmarks") Color.Black else textColor,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                }
            },
            text = {
                if (selectedTocTab == "units") {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 380.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(edition.units) { unit ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        currentPage = unit.pageStart
                                        showTocModal = false
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = if (currentUnit?.unitNumber == unit.unitNumber) EmeraldPrimary.copy(alpha = 0.2f) else viewerBg,
                                border = BorderStroke(1.dp, if (currentUnit?.unitNumber == unit.unitNumber) EmeraldPrimary else barBorderColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = unit.unitNumber.uppercase(),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = GoldAccent
                                        )
                                        Text(
                                            text = unit.title,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = textColor,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = barBg
                                    ) {
                                        Text(
                                            text = "Page ${unit.pageStart}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = EmeraldPrimary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (editionBookmarks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No bookmarks saved for this edition yet. Tap the bookmark icon on any page to save it here!",
                                style = MaterialTheme.typography.bodySmall,
                                color = textMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 380.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(editionBookmarks) { pageNum ->
                                val pageUnit = edition.units.findLast { it.pageStart <= pageNum } ?: edition.units.firstOrNull()
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            currentPage = pageNum
                                            showTocModal = false
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (currentPage == pageNum) GoldAccent.copy(alpha = 0.15f) else viewerBg,
                                    border = BorderStroke(1.dp, if (currentPage == pageNum) GoldAccent else barBorderColor)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Default.Bookmark, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text(
                                                    text = "Page $pageNum",
                                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = textColor
                                                )
                                                Text(
                                                    text = "${pageUnit?.unitNumber ?: "Chapter"}: ${pageUnit?.title ?: ""}",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = textMuted,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }

                                        IconButton(
                                            onClick = {
                                                if (viewModel != null) {
                                                    viewModel.toggleSaveTextbookBookmark(edition.fileName, pageNum)
                                                } else {
                                                    fallbackBookmarks = fallbackBookmarks - pageNum
                                                }
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.Close, contentDescription = "Delete", tint = textMuted, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}

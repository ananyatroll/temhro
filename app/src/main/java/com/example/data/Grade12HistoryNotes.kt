package com.example.data

object Grade12HistoryNotes {

    fun getGrade12HistoryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_hist"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_hist_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 12"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Development of Capitalism and Nationalism from 1815 to 1914",
            "Development of Capitalism / The Industrial Revolution and Imperialism / Unification of Italy and Germany",
            """
            • Capitalism emerged in Northwestern Europe (Great Britain and the Netherlands) from the 16th to 17th centuries during the era of mercantilism.
            • Mercantilism: Defined as the distribution of goods bought at a certain price and sold at a higher price to generate profits.
            • Marxian Economics: Named after Karl Marx, it is the antithesis of capitalism; Marx argued that capitalism causes class segregation and exploitation of the surplus value of labor.
            • Laissez-faire: An economic philosophy suggesting that minimal government interference would benefit practitioners and promote general welfare.
            • Industrialization created a new class system: the industrial capitalist class (bourgeoisie) and the industrial proletariat (wage workers).
            • Imperialism: The expanding and accelerated drive for colonial acquisitions and spheres of influence in the late 19th century, driven by the need for raw materials and markets.
            • Social Darwinism: A pseudo-science that spread the idea that history is a struggle between nations for power and survival; the strongest nations would victor while the weak would be destroyed.
            • Count Camillo di Cavour: The architect of Italian unification "from above"; he led Piedmont-Sardinia in the unification process.
            • Giuseppe Garibaldi: A skilled military leader who led the Sicilian uprising and the "Thousand Volunteers" to unite southern Italy.
            • Otto von Bismarck: The key figure in German unification, using "blood and iron" to unite the German states under Prussian leadership.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Africa and the Colonial Experience (1880s - 1960s)",
            "Colonial Empires and Resistance",
            """
            • Legitimate Trade: The transition from the slave trade to trade in natural products like palm oil, cocoa, and rubber.
            • African Resistance: Took two forms—violent and non-violent; West Africa achieved independence with relative ease due to fewer white settlers.
            • Leaders of Independence: Nnamdi Azikwe (Nigeria) and Kwame Nkrumah (Ghana) were key figures in West African nationalist movements.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Social, Economic and Political Developments in Ethiopia, Mid 19th C. to 1941",
            "Power Rivalry and Consolidating Central Government / External Relations and the Treaty of Wuchale",
            """
            • Emperor Yohannis IV: Fought successful wars against Egyptian invaders and Mahadist forces; he died at the Battle of Metemma in 1889.
            • Emperor Menilek II: Completed the national unification process; the size of the kingdom doubled during his reign.
            • Victory of Adwa (1896): A remarkable victory over Italian forces that secured Ethiopia's internationally recognized modern boundaries.
            • Hewett (Adwa) Treaty of 1884: Signed between Yohannis IV and Britain; Britain promised to return Bogos to Ethiopia in exchange for safe evacuation of Egyptian troops.
            • Treaty of Wuchale (1889): Signed between Menilek and Italy; Article III delimited the boundary, but the Italians cheated in the translation of Article XVII, leading to conflict.
            • Dogali Incident (1887): Ras Alula destroyed an Italian army of 500 soldiers, the first serious blow to Italian colonial advance.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Society and Politics in the Age of World Wars, 1914 - 1945",
            "World War I and Alliances / World War II and Italian Occupation",
            """
            • Dual Alliance (1879): Between Germany and Austria-Hungary to isolate France and counter Russia.
            • Triple Alliance (1882): Expanded the Dual Alliance to include Italy.
            • Entente Cordiale (1904): An agreement between Britain and France resolving colonial differences in Egypt and Morocco.
            • Fascist Occupation (1936-1941): Ethiopia became part of Italian East Africa (AOI) with Eritrea and Somaliland; Addis Ababa was the capital.
            • Patriotic Resistance: Divided into two phases—conventional fighting (1935-36) and guerrilla warfare starting after the February 1937 massacre.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Global and Regional Developments Since 1945",
            "Post-War Europe and the Cold War / Cold War Conflicts and Reforms",
            """
            • Bipolarization: The rivalry for influence between the USA (West) and the Soviet Union (Eastern Bloc) after WWII.
            • Marshall Plan: Economic assistance provided by the USA to help Western European nations recover.
            • Independence of India (1947): India (led by Nehru) and Pakistan (led by Jinnah) gained independence from British rule.
            • People's Republic of China (1949): Established by Mao Zedong after the CCP defeated the Kuomintang (KMT) in the civil war.
            • Vietnam War: The USA intervened to prevent a Communist victory in the South; the war ended in 1975 with a North Vietnamese victory.
            • Mikhail Gorbachev: Introduced reforms known as Perestroika (restructuring) and Glasnost (openness) in 1985 to revitalize the USSR.
            • Chernobyl Accident (1986): The worst nuclear power accident in history, occurring in the Ukrainian Republic of the USSR.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Ethiopia: Internal Developments and External Influences from 1941 to 1991",
            "Restoration of Imperial Rule and Eritrea / The 1974 Revolution and the Derg",
            """
            • Eritrean Question: Resolved by the UN in 1950 by federating Eritrea with Ethiopia; the federation was put into effect in 1952.
            • Kagnew Station: A US communications monitoring station in Asmara, leased for 25 years starting in 1953.
            • Woyane Rebellion (1943): A peasant rebellion in Tigray led by Blatta Hayle Mariam Redda against the imperial regime.
            • Bale Peasant Rebellion (1963-1970): Driven by local discontents over land and tax issues and supported by Somalia.
            • Student Movement: Progressed through three phases, becoming militant in the mid-1960s with the slogan "Land to the Tiller!"
            • The Derg: The military regime that overthrew Emperor Haile Selassie in 1974 and ruled until 1991.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Africa since the 1960s",
            "Road to Independence",
            """
            • Portuguese Colonies: Angola (led by MPLA) and Mozambique (led by FRELIMO) achieved independence in 1975 after violent struggles.
            • OAU to AU: The Organization of African Unity (OAU) was replaced by the African Union (AU), officially inaugurated in July 2002 in Durban.
            • Sirte Declaration (1999): Called for the establishment of the AU to hasten African integration.
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Post 1991 Developments in Ethiopia",
            "The Transitional Government and Constitution",
            """
            • Transitional Government of Ethiopia (TGE): Formed in 1991 after the EPRDF overthrew the Derg; it operated under the Transitional Period Charter.
            • 1995 Constitution: Established the Federal Democratic Republic of Ethiopia (FDRE) and introduced ethnic-based administrative regions.
            • Operation Sunset (1999): A massive Ethiopian military offensive to restore Badme during the Ethio-Eritrean war.
            • Algiers Agreement (2000): Ended the Ethio-Eritrean war, leading to the establishment of UNMEE.
            """.trimIndent()
        )

        // Unit 9
        addNote(
            "Unit 9 - Indigenous Knowledge Systems and Heritages of Ethiopia",
            "Indigenous Knowledge",
            """
            • Definition: Knowledge systems developed by local communities over generations, unique to their culture and environment.
            • Hydro-Politics of the Nile: Ethiopia was excluded from the 1929 and 1959 Nile treaties; the Grand Ethiopian Renaissance Dam (GERD) was founded to assert Ethiopia's rights.
            """.trimIndent()
        )

        return notesList
    }
}
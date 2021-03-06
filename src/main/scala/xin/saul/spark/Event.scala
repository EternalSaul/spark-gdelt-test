package xin.saul.spark

case class Event(
                  GlobalEventID: Long,
                  Day: Long,
                  MonthYear: Long,
                  Year: Long,
                  FractionDate: Double,
                  Actor1Code: String,
                  Actor1Name: String,
                  Actor1CountryCode: String,
                  Actor1KnownGroupCode: String,
                  Actor1EthnicCode: String,
                  Actor1Religion1Code: String,
                  Actor1Religion2Code: String,
                  Actor1Type1Code: String,
                  Actor1Type2Code: String,
                  Actor1Type3Code: String,
                  Actor2Code: String,
                  Actor2Name: String,
                  Actor2CountryCode: String,
                  Actor2KnownGroupCode: String,
                  Actor2EthnicCode: String,
                  Actor2Religion1Code: String,
                  Actor2Religion2Code: String,
                  Actor2Type1Code: String,
                  Actor2Type2Code: String,
                  Actor2Type3Code: String,
                  IsRootEvent: Long,
                  EventCode: String,
                  EventBaseCode: String,
                  EventRootCode: String,
                  QuadClass: Long,
                  GoldsteinScale: Double,
                  NumMentions: Long,
                  NumSources: Long,
                  NumArticles: Long,
                  AvgTone: Double,
                  Actor1Geo_Type: Long,
                  Actor1Geo_Fullname: String,
                  Actor1Geo_CountryCode: String,
                  Actor1Geo_ADM1Code: String,
                  Actor1Geo_ADM2Code: String,
                  Actor1Geo_Lat: Double,
                  Actor1Geo_Long: Double,
                  Actor1Geo_FeatureID: String,
                  Actor2Geo_Type: Long,
                  Actor2Geo_Fullname: String,
                  Actor2Geo_CountryCode: String,
                  Actor2Geo_ADM1Code: String,
                  Actor2Geo_ADM2Code: String,
                  Actor2Geo_Lat: Double,
                  Actor2Geo_Long: Double,
                  Actor2Geo_FeatureID: String,
                  ActionGeo_Type: Long,
                  ActionGeo_Fullname: String,
                  ActionGeo_CountryCode: String,
                  ActionGeo_ADM1Code: String,
                  ActionGeo_ADM2Code: String,
                  ActionGeo_Lat: Double,
                  ActionGeo_Long: Double,
                  ActionGeo_FeatureID: String,
                  DATEADDED: Long,
                  SOURCEURL: String
                )
